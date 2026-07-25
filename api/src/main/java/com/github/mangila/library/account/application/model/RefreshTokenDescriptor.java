package com.github.mangila.library.account.application.model;

import java.util.UUID;

public record RefreshTokenDescriptor(UUID accountId, String userAgent) {}
