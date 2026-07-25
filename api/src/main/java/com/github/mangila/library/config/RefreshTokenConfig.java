package com.github.mangila.library.config;

import io.smallrye.config.ConfigMapping;
import java.time.Duration;

@ConfigMapping(prefix = "app.auth.refresh-token")
public interface RefreshTokenConfig {

  Duration expiration();
}
