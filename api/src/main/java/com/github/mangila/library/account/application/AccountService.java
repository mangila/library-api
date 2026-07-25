package com.github.mangila.library.account.application;

import com.github.mangila.library.account.data.*;
import com.github.mangila.library.account.rest.model.*;
import com.github.mangila.library.account.shared.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountService {

  private final JwtTokenProvider jwtTokenProvider;
  private final RefreshTokenProvider refreshTokenProvider;
  private final AccountMapper accountMapper;
  private final AccountDataService accountDataService;

  public AccountService(
      JwtTokenProvider jwtTokenProvider,
      RefreshTokenProvider refreshTokenProvider,
      AccountMapper accountMapper,
      AccountDataService accountDataService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.refreshTokenProvider = refreshTokenProvider;
    this.accountMapper = accountMapper;
    this.accountDataService = accountDataService;
  }

  @Transactional
  public Optional<Account> findByIdOptional(UUID id) {
    return accountDataService.findByIdOptional(id).map(accountMapper::toDomain);
  }

  @Transactional(Transactional.TxType.MANDATORY)
  public Optional<Account> findByUsernameOptionalForUpdate(String username) {
    return accountDataService
        .findByUsernameOptionalForUpdate(username)
        .map(accountMapper::toDomain);
  }

  public JwtToken issueJwtToken(Account account) {
    final JwtTokenDescriptor jwtTokenDescriptor = JwtTokenDescriptor.from(account);
    return jwtTokenProvider.get(jwtTokenDescriptor);
  }

  @Transactional
  public RefreshToken issueRefreshToken(Account account) {
    final RefreshToken refreshToken = refreshTokenProvider.get();
    final AccountEntity accountEntity = accountMapper.toEntity(account);
    accountEntity.setRefreshToken(refreshToken.asHashedToken());
    accountDataService.merge(accountEntity);
    return refreshToken;
  }

  @Transactional
  public void persist(Account account) {
    final AccountEntity accountEntity = accountMapper.toEntity(account);
    accountDataService.persist(accountEntity);
  }
}
