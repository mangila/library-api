package com.github.mangila.library.account.data;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<AccountEntity, UUID> {

  public Optional<AccountEntity> findByUsernameOptionalForUpdate(String username) {
    final Duration lockTimeout = Duration.ofSeconds(5);
    return find("username", username)
        .withLock(LockModeType.PESSIMISTIC_WRITE)
        .withHint("jakarta.persistence.lock.timeout", (int) lockTimeout.toMillis())
        .firstResultOptional();
  }
}
