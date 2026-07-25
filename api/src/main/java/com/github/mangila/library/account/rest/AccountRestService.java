package com.github.mangila.library.account.rest;

import com.github.mangila.library.account.application.Account;
import com.github.mangila.library.account.application.AccountFactory;
import com.github.mangila.library.account.application.AccountService;
import com.github.mangila.library.account.rest.model.*;
import com.github.mangila.library.account.shared.AccountMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidProvider;
import io.github.mangila.ensure4j.Ensure;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class AccountRestService {

  private final UuidProvider uuidProvider;
  private final AccountService accountService;
  private final AccountMapper accountMapper;
  private final AccountFactory accountFactory;

  public AccountRestService(
      UuidProvider uuidProvider,
      AccountService accountService,
      AccountMapper accountMapper,
      AccountFactory accountFactory) {
    this.uuidProvider = uuidProvider;
    this.accountService = accountService;
    this.accountMapper = accountMapper;
    this.accountFactory = accountFactory;
  }

  public TokenResponse login(LoginRequest request) {
    final Account account =
        accountService
            .findByUsernameOptional(request.username())
            .orElseThrow(
                () -> HttpProblemException.unauthorized("Username or password was not correct"));
    Ensure.isTrue(
        BcryptUtil.matches(request.password(), account.password()),
        () -> HttpProblemException.unauthorized("Username or password was not correct"));
    Ensure.isTrue(
        account.active(), () -> HttpProblemException.unauthorized("Account is not active"));
    final JwtToken jwtToken = accountService.issueJwtToken(account);
    final RefreshToken refreshToken = accountService.issueRefreshToken(account);
    return new TokenResponse(jwtToken, refreshToken);
  }

  public AccountMeResponse me(JsonWebToken jwt) {
    final String subject = jwt.getSubject();
    final UUID accountId = uuidProvider.parse(subject);
    final AccountDto accountDto =
        accountService
            .findByIdOptional(accountId)
            .map(accountMapper::toDto)
            .orElseThrow(() -> HttpProblemException.notFound("Account not found"));
    return new AccountMeResponse(jwt, accountDto);
  }

  public TokenResponse refresh(RefreshRequest request) {
    final Account account =
        accountService
            .findByUsernameOptional(request.username())
            .orElseThrow(() -> HttpProblemException.unauthorized("Refresh token was not correct"));
    RefreshToken refreshToken = account.refreshToken();
    Ensure.isTrue(
        refreshToken.matchesHash(request.refreshToken()),
        () -> HttpProblemException.unauthorized("Refresh token has expired"));
    Ensure.isFalse(
        refreshToken.hasExpired(),
        () -> HttpProblemException.unauthorized("Refresh token has expired"));
    Ensure.isTrue(
        account.active(), () -> HttpProblemException.unauthorized("Account is not active"));
    final JwtToken jwtToken = accountService.issueJwtToken(account);
    refreshToken = accountService.issueRefreshToken(account);
    return new TokenResponse(jwtToken, refreshToken);
  }

  @Transactional
  public TokenResponse signup(SignupRequest request) {
    final Optional<Account> optionalAccount =
        accountService.findByUsernameOptional(request.username());
    Ensure.isTrue(
        optionalAccount.isEmpty(),
        () -> HttpProblemException.unauthorized("Username already exists"));
    final Account account = accountFactory.from(request, List.of("USER"));
    accountService.persist(account);
    final JwtToken jwtToken = accountService.issueJwtToken(account);
    final RefreshToken refreshToken = accountService.issueRefreshToken(account);
    return new TokenResponse(jwtToken, refreshToken);
  }
}
