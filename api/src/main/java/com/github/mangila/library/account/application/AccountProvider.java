package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.Account;
import com.github.mangila.library.account.rest.model.SignupRequest;
import com.github.mangila.library.config.AccountConfig;
import com.github.mangila.library.shared.UuidProvider;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountProvider {

  private final AccountConfig accountConfig;
  private final UuidProvider uuidProvider;

  public AccountProvider(AccountConfig accountConfig, UuidProvider uuidProvider) {
    this.accountConfig = accountConfig;
    this.uuidProvider = uuidProvider;
  }

  public Account get(SignupRequest request) {
    final List<String> roles = accountConfig.defaultRoles();
    return get(request, roles);
  }

  public Account get(SignupRequest request, List<String> roles) {
    final UUID id = uuidProvider.generate();
    final String username = request.username();
    final String hashedPassword = BcryptUtil.bcryptHash(request.password());
    final boolean active = true;
    return new Account(id, username, hashedPassword, roles, active, null, null, null);
  }
}
