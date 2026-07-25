package com.github.mangila.library.config;

import com.github.mangila.library.account.shared.Password;
import com.github.mangila.library.account.shared.Username;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.admin")
public interface AdminConfig {

  @Password
  String password();

  boolean seed();

  @Username
  String username();
}
