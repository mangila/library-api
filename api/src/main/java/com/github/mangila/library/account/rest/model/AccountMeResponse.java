package com.github.mangila.library.account.rest.model;

import java.util.List;
import org.eclipse.microprofile.jwt.JsonWebToken;

public record AccountMeResponse(
    JsonWebToken jwt, AccountDto account, List<RefreshTokenDto> refreshTokens) {}
