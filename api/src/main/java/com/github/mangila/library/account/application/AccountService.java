package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.Account;
import com.github.mangila.library.account.application.model.AccountSetting;
import com.github.mangila.library.account.data.AccountRepository;
import com.github.mangila.library.account.data.AccountSettingRepository;
import com.github.mangila.library.account.data.model.AccountEntity;
import com.github.mangila.library.account.data.model.AccountSettingEntity;
import com.github.mangila.library.account.shared.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountService {

  private final AccountMapper accountMapper;
  private final AccountRepository accountRepository;
  private final AccountSettingRepository accountSettingRepository;

  public AccountService(
      AccountMapper accountMapper,
      AccountRepository accountRepository,
      AccountSettingRepository accountSettingRepository) {
    this.accountMapper = accountMapper;
    this.accountRepository = accountRepository;
    this.accountSettingRepository = accountSettingRepository;
  }

  @Transactional
  public Optional<Account> findByIdOptional(UUID id) {
    return accountRepository.findByIdOptional(id).map(accountMapper::toDomain);
  }

  @Transactional
  public Optional<Account> findByUsernameOptional(String username) {
    return accountRepository
        .find("username", username)
        .firstResultOptional()
        .map(accountMapper::toDomain);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  public Optional<Account> findByUsernameOptionalLock(String username) {
    return accountRepository.findByUsernameOptionalLock(username).map(accountMapper::toDomain);
  }

  @Transactional
  public void persist(Account account) {
    final AccountEntity accountEntity = accountMapper.toEntity(account);
    accountRepository.persist(accountEntity);
  }

  @Transactional
  public void persist(AccountSetting accountSetting) {
    final AccountSettingEntity accountSettingEntity = accountMapper.toEntity(accountSetting);
    accountSettingRepository.persist(accountSettingEntity);
  }
}
