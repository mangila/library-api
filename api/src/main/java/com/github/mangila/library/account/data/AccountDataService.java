package com.github.mangila.library.account.data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountDataService {

  private final AccountRepository accountRepository;

  public AccountDataService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Transactional
  public Optional<AccountEntity> findByIdOptional(UUID id) {
    return accountRepository.findByIdOptional(id);
  }

  @Transactional
  public Optional<AccountEntity> findByUsernameOptional(String username) {
    return accountRepository.findByUsernameOptional(username);
  }

  @Transactional
  public AccountEntity merge(AccountEntity accountEntity) {
    return accountRepository.getEntityManager().merge(accountEntity);
  }

  @Transactional
  public void persist(AccountEntity accountEntity) {
    accountRepository.persist(accountEntity);
  }
}
