package com.github.mangila.library.account.application;

import com.github.mangila.library.account.application.model.JwtToken;
import com.github.mangila.library.account.application.model.JwtTokenDescriptor;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class JwtTokenProvider {

  public JwtToken get(JwtTokenDescriptor descriptor) {
    final String subject = descriptor.subject();
    final String upn = descriptor.upn();
    final Set<String> groups = descriptor.groups();
    final String token = Jwt.subject(subject).upn(upn).groups(groups).sign();
    return new JwtToken(token);
  }
}
