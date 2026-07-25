package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.RefreshToken;
import com.github.mangila.library.account.application.model.RefreshTokenDescriptor;
import com.github.mangila.library.account.data.AccountRefreshTokenRepository;
import com.github.mangila.library.account.data.model.AccountRefreshTokenEntity;
import com.github.mangila.library.account.shared.RefreshTokenMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RefreshTokenService {

  private final RefreshTokenProvider refreshTokenProvider;
  private final AccountRefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenMapper refreshTokenMapper;

  public RefreshTokenService(
      RefreshTokenProvider refreshTokenProvider,
      AccountRefreshTokenRepository refreshTokenRepository,
      RefreshTokenMapper refreshTokenMapper) {
    this.refreshTokenProvider = refreshTokenProvider;
    this.refreshTokenRepository = refreshTokenRepository;
    this.refreshTokenMapper = refreshTokenMapper;
  }

  @Transactional
  public boolean deleteById(UUID id) {
    return refreshTokenRepository.deleteById(id);
  }

  @Transactional
  public List<RefreshToken> findAllByAccountId(UUID accountId) {
    return refreshTokenRepository.find("accountId", accountId).list().stream()
        .map(refreshTokenMapper::toDomain)
        .toList();
  }

  @Transactional(Transactional.TxType.MANDATORY)
  public Optional<RefreshToken> findByTokenOptionalLock(String hashedToken) {
    final Duration lockTimeout = Duration.ofSeconds(5);
    return refreshTokenRepository
        .find("token", hashedToken)
        .withLock(LockModeType.PESSIMISTIC_WRITE)
        .withHint("jakarta.persistence.lock.timeout", (int) lockTimeout.toMillis())
        .firstResultOptional()
        .map(refreshTokenMapper::toDomain);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  public RefreshToken issueNewToken(UUID accountId, String userAgent) {
    final RefreshTokenDescriptor descriptor = new RefreshTokenDescriptor(accountId, userAgent);
    final RefreshToken refreshToken = refreshTokenProvider.get(descriptor);
    final RefreshToken hashedToken = refreshToken.asHashedToken();
    final AccountRefreshTokenEntity entity = refreshTokenMapper.toEntity(hashedToken);
    refreshTokenRepository.persist(entity);
    return refreshToken;
  }
}
