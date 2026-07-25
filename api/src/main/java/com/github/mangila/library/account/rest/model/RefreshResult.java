package com.github.mangila.library.account.rest.model;

import com.github.mangila.library.account.application.model.JwtToken;
import com.github.mangila.library.account.application.model.RefreshToken;

public record RefreshResult(JwtToken jwtToken, RefreshToken refreshToken) {}
