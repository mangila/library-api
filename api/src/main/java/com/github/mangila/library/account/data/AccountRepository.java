package com.github.mangila.library.account.data;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<AccountEntity, UUID> {

  public Optional<AccountEntity> findByUsernameOptional(String username) {
    return find("username", username).firstResultOptional();
  }
}
