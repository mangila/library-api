package com.github.mangila.library.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import jakarta.validation.constraints.NotBlank;
import java.time.Duration;

@ConfigMapping(prefix = "app.cookie")
public interface CookieConfig {

  RefreshToken refreshToken();

  interface RefreshToken {
    @WithDefault("true")
    boolean httpOnly();

    Duration maxAge();

    @NotBlank String name();

    @NotBlank String path();

    @WithDefault("true")
    boolean secure();
  }
}
