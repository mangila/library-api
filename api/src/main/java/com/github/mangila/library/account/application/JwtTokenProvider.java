package com.github.mangila.library.account.application;

import com.github.mangila.library.account.rest.model.JwtToken;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtTokenProvider {

  private static final String AUDIENCE = "library-api";
  private static final Duration EXPIRES_IN = Duration.ofMinutes(15);

  public JwtToken get(JwtTokenDescriptor descriptor) {
    final String subject = descriptor.subject();
    final String upn = descriptor.upn();
    final Set<String> groups = descriptor.groups();
    final String token =
        Jwt.issuer("http://localhost:8080")
            .subject(subject)
            .upn(upn)
            .groups(groups)
            .audience(AUDIENCE)
            .expiresIn(EXPIRES_IN)
            .sign();
    return new JwtToken(token);
  }
}
