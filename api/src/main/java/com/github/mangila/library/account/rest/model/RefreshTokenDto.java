package com.github.mangila.library.account.rest.model;

import java.time.Instant;
import java.util.UUID;

public record RefreshTokenDto(UUID id, UUID accountId, Instant expiresAt, String userAgent) {}
