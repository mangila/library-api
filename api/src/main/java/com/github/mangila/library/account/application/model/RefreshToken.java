package com.github.mangila.library.account.application.model;

import com.github.mangila.library.shared.Sha256;
import java.time.Instant;
import java.util.UUID;

public record RefreshToken(
    UUID id, UUID accountId, String value, Instant expiresAt, String userAgent) {

  public boolean hasExpired() {
    return Instant.now().isAfter(expiresAt);
  }

  public RefreshToken asHashedToken() {
    final String sha256Hex = Sha256.hash(value);
    return new RefreshToken(id, accountId, sha256Hex, expiresAt, userAgent);
  }
}
