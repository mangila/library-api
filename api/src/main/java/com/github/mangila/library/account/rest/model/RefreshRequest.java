package com.github.mangila.library.account.rest.model;

import com.github.mangila.library.account.shared.Username;
import org.hibernate.validator.constraints.UUID;

public record RefreshRequest(@Username String username, @UUID String refreshToken) {}
