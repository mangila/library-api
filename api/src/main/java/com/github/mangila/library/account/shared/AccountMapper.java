package com.github.mangila.library.account.shared;

import com.github.mangila.library.account.application.Account;
import com.github.mangila.library.account.data.AccountEntity;
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
        accountEntity.getRefreshToken(),
        accountEntity.isActive(),
        accountEntity.getCreatedAt(),
        accountEntity.getUpdatedAt());
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
    return accountEntity;
  }
}
