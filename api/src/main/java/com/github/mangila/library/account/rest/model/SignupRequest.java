package com.github.mangila.library.account.rest.model;

import com.github.mangila.library.account.shared.Password;
import com.github.mangila.library.account.shared.Username;

public record SignupRequest(@Username String username, @Password String password) {}
