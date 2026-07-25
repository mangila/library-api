package com.github.mangila.library.account.rest;

import com.github.mangila.library.account.application.AccountService;
import com.github.mangila.library.account.application.JwtTokenService;
import com.github.mangila.library.account.application.RefreshTokenService;
import com.github.mangila.library.account.application.model.Account;
import com.github.mangila.library.account.application.model.JwtToken;
import com.github.mangila.library.account.application.model.RefreshToken;
import com.github.mangila.library.account.rest.model.RefreshResult;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.Sha256;
import io.github.mangila.ensure4j.Ensure;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class RefreshTokenRestService {

  private final RefreshTokenService refreshTokenService;
  private final AccountService accountService;
  private final JwtTokenService jwtTokenService;

  public RefreshTokenRestService(
      RefreshTokenService refreshTokenService,
      AccountService accountService,
      JwtTokenService jwtTokenService) {
    this.refreshTokenService = refreshTokenService;
    this.accountService = accountService;
    this.jwtTokenService = jwtTokenService;
  }

  @Transactional
  public RefreshResult refresh(String token, String userAgent) {
    try {
      final String hashedToken = Sha256.hash(token);
      RefreshToken refreshToken =
          refreshTokenService
              .findByTokenOptionalLock(hashedToken)
              .orElseThrow(() -> new IllegalStateException("Refresh token not found"));
      Ensure.isTrue(
          Objects.equals(refreshToken.userAgent(), userAgent),
          () -> new IllegalStateException("User agent mismatch"));
      Ensure.isFalse(refreshToken.hasExpired(), "Refresh token has expired");
      final UUID accountId = refreshToken.accountId();
      final boolean deleted = refreshTokenService.deleteById(refreshToken.id());
      Ensure.isTrue(deleted, "Failed to delete refresh token");
      refreshToken = refreshTokenService.issueNewToken(accountId, userAgent);
      final Account account =
          accountService
              .findByIdOptional(accountId)
              .orElseThrow(() -> new IllegalStateException("Account not found"));
      final JwtToken jwtToken = jwtTokenService.issueNewToken(account);
      return new RefreshResult(jwtToken, refreshToken);
    } catch (IllegalStateException e) {
      Log.error("ERR", e);
      throw HttpProblemException.unauthorized("Failed to refresh token");
    }
  }
}
