package com.github.mangila.library.account.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignUpResponse(@JsonProperty("account") AccountDto accountDto) {}
