package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.Account;
import com.github.mangila.library.account.application.model.JwtToken;
import com.github.mangila.library.account.application.model.JwtTokenDescriptor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtTokenService {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenService(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public JwtToken issueNewToken(Account account) {
    JwtTokenDescriptor jwtTokenDescriptor = JwtTokenDescriptor.from(account);
    return jwtTokenProvider.get(jwtTokenDescriptor);
  }
}
