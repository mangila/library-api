package com.github.mangila.library.account.application.model;

public record TokenResult(JwtToken accessToken, RefreshToken refreshToken) {}
