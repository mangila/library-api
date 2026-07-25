package com.github.mangila.library.account.application.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Account(
    UUID id,
    String username,
    String password,
    List<String> roles,
    boolean active,
    Instant createdAt,
    Instant updatedAt,
    Long version) {}
