package com.github.mangila.library.account.data;

import com.github.mangila.library.account.rest.model.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Entity(name = "Accounts")
@Table(name = "accounts")
public class AccountEntity {

  @Id private UUID id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "roles")
  @JdbcTypeCode(SqlTypes.JSON_ARRAY)
  private List<String> roles;

  @Column(name = "refresh_token")
  @JdbcTypeCode(SqlTypes.JSON)
  private RefreshToken refreshToken;

  @Column(name = "active")
  private boolean active;

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  public Instant getCreatedAt() {
    return createdAt;
  }

  public UUID getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public RefreshToken getRefreshToken() {
    return refreshToken;
  }

  public List<String> getRoles() {
    return roles;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public String getUsername() {
    return username;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRefreshToken(RefreshToken refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
