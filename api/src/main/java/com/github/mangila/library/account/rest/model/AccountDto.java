package com.github.mangila.library.account.rest.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AccountDto(
    UUID id,
    String username,
    List<String> roles,
    boolean active,
    Instant createdAt,
    Instant updatedAt) {}
