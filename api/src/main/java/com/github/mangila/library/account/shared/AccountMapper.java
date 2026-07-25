package com.github.mangila.library.account.shared;

import com.github.mangila.library.account.application.model.Account;
import com.github.mangila.library.account.application.model.AccountSetting;
import com.github.mangila.library.account.data.model.AccountEntity;
import com.github.mangila.library.account.data.model.AccountSettingEntity;
import com.github.mangila.library.account.rest.model.AccountDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountMapper {

  public Account toDomain(AccountEntity accountEntity) {
    return new Account(
        accountEntity.getId(),
        accountEntity.getUsername(),
        accountEntity.getPassword(),
        accountEntity.getRoles(),
        accountEntity.isActive(),
        accountEntity.getCreatedAt(),
        accountEntity.getUpdatedAt(),
        accountEntity.getVersion());
  }

  public AccountDto toDto(Account account) {
    return new AccountDto(
        account.id(),
        account.username(),
        account.roles(),
        account.active(),
        account.createdAt(),
        account.updatedAt());
  }

  public AccountEntity toEntity(Account account) {
    final AccountEntity accountEntity = new AccountEntity();
    accountEntity.setId(account.id());
    accountEntity.setUsername(account.username());
    accountEntity.setPassword(account.password());
    accountEntity.setRoles(account.roles());
    accountEntity.setActive(account.active());
    accountEntity.setCreatedAt(account.createdAt());
    accountEntity.setUpdatedAt(account.updatedAt());
    accountEntity.setVersion(account.version());
    return accountEntity;
  }

  public AccountSettingEntity toEntity(AccountSetting accountSetting) {
    final AccountSettingEntity accountSettingEntity = new AccountSettingEntity();
    accountSettingEntity.setAccountId(accountSetting.accountId());
    return accountSettingEntity;
  }
}
