package com.github.mangila.library.account.shared;

import com.github.mangila.library.account.application.model.RefreshToken;
import com.github.mangila.library.account.data.model.AccountRefreshTokenEntity;
import com.github.mangila.library.account.rest.model.RefreshTokenDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefreshTokenMapper {

  public RefreshToken toDomain(AccountRefreshTokenEntity accountRefreshTokenEntity) {
    return new RefreshToken(
        accountRefreshTokenEntity.getId(),
        accountRefreshTokenEntity.getAccountId(),
        accountRefreshTokenEntity.getToken(),
        accountRefreshTokenEntity.getExpiresAt(),
        accountRefreshTokenEntity.getUserAgent());
  }

  public RefreshTokenDto toDto(RefreshToken refreshToken) {
    return new RefreshTokenDto(
        refreshToken.id(),
        refreshToken.accountId(),
        refreshToken.expiresAt(),
        refreshToken.userAgent());
  }

  public AccountRefreshTokenEntity toEntity(RefreshToken refreshToken) {
    final AccountRefreshTokenEntity entity = new AccountRefreshTokenEntity();
    entity.setId(refreshToken.id());
    entity.setAccountId(refreshToken.accountId());
    entity.setToken(refreshToken.value());
    entity.setUserAgent(refreshToken.userAgent());
    entity.setExpiresAt(refreshToken.expiresAt());
    return entity;
  }
}
