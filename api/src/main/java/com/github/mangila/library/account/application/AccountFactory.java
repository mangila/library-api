package com.github.mangila.library.account.application;

import com.github.mangila.library.account.rest.model.SignupRequest;
import com.github.mangila.library.shared.UuidProvider;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountFactory {

  private final UuidProvider uuidProvider;

  public AccountFactory(UuidProvider uuidProvider) {
    this.uuidProvider = uuidProvider;
  }

  public Account from(SignupRequest request, List<String> roles) {
    final UUID id = uuidProvider.generate();
    final String hashedPassword = BcryptUtil.bcryptHash(request.password());
    return new Account(id, request.username(), hashedPassword, roles, null, true, null, null);
  }
}
