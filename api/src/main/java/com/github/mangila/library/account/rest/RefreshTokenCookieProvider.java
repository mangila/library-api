package com.github.mangila.library.account.rest;

import com.github.mangila.library.account.application.model.RefreshToken;
import com.github.mangila.library.config.CookieConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.NewCookie;
import java.time.Duration;

@ApplicationScoped
public class RefreshTokenCookieProvider {

  private final CookieConfig.RefreshToken refreshTokenConfig;

  public RefreshTokenCookieProvider(CookieConfig cookieConfig) {
    this.refreshTokenConfig = cookieConfig.refreshToken();
  }

  public NewCookie get(RefreshToken refreshToken) {
    final String name = refreshTokenConfig.name();
    final String path = refreshTokenConfig.path();
    final boolean secure = refreshTokenConfig.secure();
    final boolean httpOnly = refreshTokenConfig.httpOnly();
    final Duration maxAge = refreshTokenConfig.maxAge();
    final int maxAgeInSeconds = (int) maxAge.toSeconds();
    return new NewCookie.Builder(name)
        .value(refreshToken.value())
        .path(path)
        .maxAge(maxAgeInSeconds)
        .secure(secure)
        .httpOnly(httpOnly)
        .sameSite(NewCookie.SameSite.LAX)
        .build();
  }
}
