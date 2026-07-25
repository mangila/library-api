package com.github.mangila.library.account.application;

import com.github.mangila.library.account.rest.model.RefreshToken;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Account(
    UUID id,
    String username,
    String password,
    List<String> roles,
    RefreshToken refreshToken,
    boolean active,
    Instant createdAt,
    Instant updatedAt) {}
