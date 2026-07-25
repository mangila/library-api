package com.github.mangila.library.account.data;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountDataService {

  private final AccountRepository accountRepository;

  public AccountDataService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Optional<AccountEntity> findByIdOptional(UUID id) {
    return accountRepository.findByIdOptional(id);
  }

  public Optional<AccountEntity> findByUsernameOptionalForUpdate(String username) {
    return accountRepository.findByUsernameOptionalForUpdate(username);
  }

  public AccountEntity merge(AccountEntity accountEntity) {
    return accountRepository.getEntityManager().merge(accountEntity);
  }

  public void persist(AccountEntity accountEntity) {
    accountRepository.persist(accountEntity);
  }
}
