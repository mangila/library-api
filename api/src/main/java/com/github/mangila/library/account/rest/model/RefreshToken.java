package com.github.mangila.library.account.rest.model;

import java.time.Instant;
import java.util.Objects;
import org.apache.commons.codec.digest.DigestUtils;

public record RefreshToken(String value, Instant expiresIn) {

  public boolean hasExpired() {
    return Instant.now().isAfter(expiresIn);
  }

  public RefreshToken asHashedToken() {
    final String sha256Hex = DigestUtils.sha256Hex(value);
    return new RefreshToken(sha256Hex, expiresIn);
  }

  public boolean matchesHash(String refreshToken) {
    final String sha256Hex = DigestUtils.sha256Hex(refreshToken);
    return Objects.equals(sha256Hex, value);
  }
}
