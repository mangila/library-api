package com.github.mangila.library.account.rest;

import com.github.mangila.library.account.application.*;
import com.github.mangila.library.account.application.model.*;
import com.github.mangila.library.account.rest.model.*;
import com.github.mangila.library.account.shared.AccountMapper;
import com.github.mangila.library.account.shared.RefreshTokenMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidProvider;
import io.github.mangila.ensure4j.Ensure;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class AccountRestService {

  private final UuidProvider uuidProvider;
  private final AccountService accountService;
  private final AccountMapper accountMapper;
  private final AccountProvider accountProvider;
  private final RefreshTokenMapper refreshTokenMapper;
  private final RefreshTokenService refreshTokenService;
  private final AccountSettingProvider accountSettingProvider;
  private final JwtTokenService jwtTokenService;

  public AccountRestService(
      UuidProvider uuidProvider,
      AccountService accountService,
      AccountMapper accountMapper,
      AccountProvider accountProvider,
      RefreshTokenMapper refreshTokenMapper,
      RefreshTokenService refreshTokenService,
      AccountSettingProvider accountSettingProvider,
      JwtTokenService jwtTokenService) {
    this.uuidProvider = uuidProvider;
    this.accountService = accountService;
    this.accountMapper = accountMapper;
    this.accountProvider = accountProvider;
    this.refreshTokenMapper = refreshTokenMapper;
    this.refreshTokenService = refreshTokenService;
    this.accountSettingProvider = accountSettingProvider;
    this.jwtTokenService = jwtTokenService;
  }

  @Transactional
  public TokenResult login(LoginRequest request, String userAgent) {
    try {
      final Account account =
          accountService
              .findByUsernameOptionalLock(request.username())
              .orElseThrow(
                  () ->
                      new IllegalStateException(
                          "Account not found: %s".formatted(request.username())));
      Ensure.isTrue(
          BcryptUtil.matches(request.password(), account.password()),
          () -> new IllegalStateException("Password was not correct"));
      Ensure.isTrue(
          account.active(),
          () ->
              new IllegalStateException("Account is not active: %s".formatted(account.username())));
      final RefreshToken refreshToken = refreshTokenService.issueNewToken(account.id(), userAgent);
      final JwtToken jwtToken = jwtTokenService.issueNewToken(account);
      return new TokenResult(jwtToken, refreshToken);
    } catch (Exception e) {
      Log.error("ERR", e);
      throw HttpProblemException.unauthorized("Failed to login");
    }
  }

  @Transactional
  public AccountMeResponse me(JsonWebToken jwt) {
    final String subject = jwt.getSubject();
    final UUID accountId = uuidProvider.parse(subject);
    final AccountDto accountDto =
        accountService
            .findByIdOptional(accountId)
            .map(accountMapper::toDto)
            .orElseThrow(() -> HttpProblemException.notFound("Account not found"));
    final List<RefreshTokenDto> refreshTokens =
        refreshTokenService.findAllByAccountId(accountId).stream()
            .map(refreshTokenMapper::toDto)
            .toList();
    return new AccountMeResponse(jwt, accountDto, refreshTokens);
  }

  @Transactional
  public SignUpResponse signup(SignupRequest request) {
    final Optional<Account> optionalAccount =
        accountService.findByUsernameOptional(request.username());
    Ensure.isTrue(
        optionalAccount.isEmpty(),
        () -> HttpProblemException.unauthorized("Username already exists"));
    final Account account = accountProvider.get(request);
    accountService.persist(account);
    final AccountSetting accountSetting = accountSettingProvider.get(account.id());
    accountService.persist(accountSetting);
    final AccountDto accountDto = accountMapper.toDto(account);
    return new SignUpResponse(accountDto);
  }
}
