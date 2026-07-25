package com.github.mangila.library.account.application.model;

import io.github.mangila.ensure4j.Ensure;
import java.util.HashSet;
import java.util.Set;

public record JwtTokenDescriptor(String subject, String upn, Set<String> groups) {

  public JwtTokenDescriptor {
    Ensure.notBlank(subject, "not blank: subject");
    Ensure.notBlank(upn, "not blank: upn");
    Ensure.notEmpty(groups, "not empty: groups");
  }

  public static JwtTokenDescriptor from(Account account) {
    final String subject = account.id().toString();
    final String upn = account.username();
    final Set<String> groups = new HashSet<>(account.roles());
    return new JwtTokenDescriptor(subject, upn, groups);
  }
}
