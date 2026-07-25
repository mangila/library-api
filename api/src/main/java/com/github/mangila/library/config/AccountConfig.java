package com.github.mangila.library.config;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@ConfigMapping(prefix = "app.auth.account")
public interface AccountConfig {

  @NotEmpty List<String> defaultRoles();
}
