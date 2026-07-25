package com.github.mangila.library.account.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity(name = "accountSetting")
@Table(name = "account_settings")
public class AccountSettingEntity {

  @Id
  @Column(name = "account_id")
  private UUID accountId;

  public AccountSettingEntity() {
    // do nothing for JPA
  }

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }
}
