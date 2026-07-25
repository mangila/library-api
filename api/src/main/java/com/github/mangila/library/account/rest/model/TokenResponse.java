package com.github.mangila.library.account.rest.model;

public record TokenResponse(JwtToken accessToken, RefreshToken refreshToken) {}
