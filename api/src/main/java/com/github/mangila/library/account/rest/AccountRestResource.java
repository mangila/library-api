package com.github.mangila.library.account.rest;

import com.github.mangila.library.account.application.model.TokenResult;
import com.github.mangila.library.account.rest.model.*;
import io.quarkus.security.Authenticated;
import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

@Path("api/v1/accounts")
public class AccountRestResource {

  private final AccountRestService accountRestService;
  private final RefreshTokenRestService refreshTokenRestService;
  private final RefreshTokenCookieProvider refreshTokenCookieProvider;

  @ConfigProperty(name = "smallrye.jwt.new-token.lifespan")
  long jwtLifespan;

  public AccountRestResource(
      AccountRestService accountRestService,
      RefreshTokenRestService refreshTokenRestService,
      RefreshTokenCookieProvider refreshTokenCookieProvider) {
    this.accountRestService = accountRestService;
    this.refreshTokenRestService = refreshTokenRestService;
    this.refreshTokenCookieProvider = refreshTokenCookieProvider;
  }

  @Path("login")
  @POST
  @PermitAll
  @RateLimit(value = 10, window = 60, windowUnit = ChronoUnit.SECONDS)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<TokenResponse> login(
      @Valid LoginRequest request, @HeaderParam("User-Agent") String userAgent) {
    final TokenResult tokenResult = accountRestService.login(request, userAgent);
    final TokenResponse response =
        new TokenResponse("Bearer", tokenResult.accessToken().value(), jwtLifespan);
    final NewCookie refreshTokenCookie = refreshTokenCookieProvider.get(tokenResult.refreshToken());
    final URI refreshUri = resourceUri("refresh");
    return RestResponse.ResponseBuilder.ok(response)
        .link(refreshUri, "refresh")
        .cookie(refreshTokenCookie)
        .build();
  }

  @Path("me")
  @GET
  @Authenticated
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<AccountMeResponse> me(@Context JsonWebToken jwt) {
    final AccountMeResponse accountMeResponse = accountRestService.me(jwt);
    return RestResponse.ok(accountMeResponse);
  }

  @Path("refresh")
  @POST
  @PermitAll
  @RateLimit(value = 10, window = 60, windowUnit = ChronoUnit.SECONDS)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<TokenResponse> refresh(
      @CookieParam("refresh_token") String refreshToken,
      @HeaderParam("User-Agent") String userAgent) {
    final RefreshResult result = refreshTokenRestService.refresh(refreshToken, userAgent);
    final TokenResponse response =
        new TokenResponse("Bearer", result.jwtToken().value(), jwtLifespan);
    final NewCookie refreshTokenCookie = refreshTokenCookieProvider.get(result.refreshToken());
    return RestResponse.ResponseBuilder.ok(response).cookie(refreshTokenCookie).build();
  }

  @Path("signup")
  @POST
  @PermitAll
  @RateLimit(value = 10, window = 60, windowUnit = ChronoUnit.SECONDS)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<SignUpResponse> signup(@Valid SignupRequest request) {
    final SignUpResponse response = accountRestService.signup(request);
    final URI location = resourceUri("me");
    final URI loginUri = resourceUri("login");
    return RestResponse.ResponseBuilder.<SignUpResponse>created(location)
        .link(loginUri, "login")
        .entity(response)
        .build();
  }

  private URI resourceUri(String methodName) {
    return UriBuilder.fromResource(AccountRestResource.class)
        .path(AccountRestResource.class, methodName)
        .build();
  }
}
