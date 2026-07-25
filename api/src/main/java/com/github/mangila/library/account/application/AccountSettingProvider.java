package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.AccountSetting;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AccountSettingProvider {

  public AccountSetting get(UUID accountId) {
    return new AccountSetting(accountId);
  }
}
