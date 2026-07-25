package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.RefreshToken;
import com.github.mangila.library.account.application.model.RefreshTokenDescriptor;
import com.github.mangila.library.config.RefreshTokenConfig;
import com.github.mangila.library.shared.UuidProvider;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class RefreshTokenProvider {

  private final UuidProvider uuidProvider;
  private final RefreshTokenConfig refreshTokenConfig;

  public RefreshTokenProvider(UuidProvider uuidProvider, RefreshTokenConfig refreshTokenConfig) {
    this.uuidProvider = uuidProvider;
    this.refreshTokenConfig = refreshTokenConfig;
  }

  public RefreshToken get(RefreshTokenDescriptor descriptor) {
    final UUID id = uuidProvider.generate();
    final UUID accountId = descriptor.accountId();
    final UUID value = uuidProvider.generate();
    final String userAgent = descriptor.userAgent();
    final Duration expiration = refreshTokenConfig.expiration();
    final Instant expiresAt = Instant.now().plus(expiration);
    return new RefreshToken(id, accountId, value.toString(), expiresAt, userAgent);
  }
}
