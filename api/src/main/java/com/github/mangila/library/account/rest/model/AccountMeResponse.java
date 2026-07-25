package com.github.mangila.library.account.rest.model;

import org.eclipse.microprofile.jwt.JsonWebToken;

public record AccountMeResponse(JsonWebToken jwt, AccountDto account) {}
