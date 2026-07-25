package com.github.mangila.library.config;

import com.github.mangila.library.account.shared.Password;
import com.github.mangila.library.account.shared.Username;
import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@ConfigMapping(prefix = "app.auth.admin")
public interface AdminConfig {

  @Password
  String password();

  @NotEmpty List<String> roles();

  boolean seed();

  @Username
  String username();
}
