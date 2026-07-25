package com.github.mangila.library.account.rest;

import com.github.mangila.library.account.rest.model.*;
import io.quarkus.security.Authenticated;
import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.temporal.ChronoUnit;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

@Path("api/v1/accounts")
public class AccountRestResource {

  private final AccountRestService accountRestService;

  public AccountRestResource(AccountRestService accountRestService) {
    this.accountRestService = accountRestService;
  }

  @Path("login")
  @POST
  @PermitAll
  @RateLimit(value = 10, window = 60, windowUnit = ChronoUnit.SECONDS)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<TokenResponse> login(@Valid LoginRequest request) {
    final TokenResponse tokenResponse = accountRestService.login(request);
    return RestResponse.ok(tokenResponse);
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
  public RestResponse<TokenResponse> refresh(@Valid RefreshRequest request) {
    final TokenResponse tokenResponse = accountRestService.refresh(request);
    return RestResponse.ok(tokenResponse);
  }

  @Path("signup")
  @POST
  @PermitAll
  @RateLimit(value = 10, window = 60, windowUnit = ChronoUnit.SECONDS)
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<TokenResponse> signup(@Valid SignupRequest request) {
    final TokenResponse tokenResponse = accountRestService.signup(request);
    final URI location =
        UriBuilder.fromResource(AccountRestResource.class)
            .path(AccountRestResource.class, "me")
            .build();
    return RestResponse.ResponseBuilder.<TokenResponse>created(location)
        .entity(tokenResponse)
        .build();
  }
}
