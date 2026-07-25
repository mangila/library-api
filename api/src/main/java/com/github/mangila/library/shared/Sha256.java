package com.github.mangila.library.shared;

import java.util.Objects;
import org.apache.commons.codec.digest.DigestUtils;

public final class Sha256 {

  public static String hash(String value) {
    return DigestUtils.sha256Hex(value);
  }

  public static boolean matches(String value, String hash) {
    if (value == null) {
      return false;
    }
    return Objects.equals(hash(value), hash);
  }

  private Sha256() {
    throw new UnsupportedOperationException("Utility class");
  }
}
