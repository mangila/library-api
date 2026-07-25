package com.github.mangila.library.account.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity(name = "accountRefreshToken")
@Table(name = "account_refresh_tokens")
public class AccountRefreshTokenEntity {

  @Id private UUID id;

  @Column(name = "account_id")
  private UUID accountId;

  @Column(name = "token")
  private String token;

  @Column(name = "expires_at")
  private Instant expiresAt;

  @Column(name = "user_agent")
  private String userAgent;

  public AccountRefreshTokenEntity() {
    // do nothing for JPA
  }

  public UUID getAccountId() {
    return accountId;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  public UUID getId() {
    return id;
  }

  public String getToken() {
    return token;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public void setAccountId(UUID accountId) {
    this.accountId = accountId;
  }

  public void setExpiresAt(Instant expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }
}
