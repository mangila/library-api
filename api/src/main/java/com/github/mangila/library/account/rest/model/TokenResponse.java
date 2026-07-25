package com.github.mangila.library.account.rest.model;

public record TokenResponse(String tokenType, String accessToken, long expirationInSeconds) {}
