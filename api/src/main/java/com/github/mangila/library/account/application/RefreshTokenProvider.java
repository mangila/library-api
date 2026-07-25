package com.github.mangila.library.account.application;

import com.github.mangila.library.account.rest.model.RefreshToken;
import com.github.mangila.library.shared.UuidProvider;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class RefreshTokenProvider {

  private static final Duration EXPIRES_IN = Duration.ofDays(10);

  private final UuidProvider uuidProvider;

  public RefreshTokenProvider(UuidProvider uuidProvider) {
    this.uuidProvider = uuidProvider;
  }

  public RefreshToken get() {
    final UUID value = uuidProvider.generate();
    final Instant expiresIn = Instant.now().plus(EXPIRES_IN);
    return new RefreshToken(value.toString(), expiresIn);
  }
}
