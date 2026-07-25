package com.github.mangila.library.account.rest.model;

import com.github.mangila.library.account.shared.Username;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.UUID;

public record RefreshRequest(@Username String username, @UUID String refreshToken) {

  public String tokenAsSha256Hex() {
    return DigestUtils.sha256Hex(refreshToken);
  }
}
