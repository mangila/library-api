package com.github.mangila.library.shared;

import java.util.UUID;

public final class UuidManager {

  public static UUID generate() {
    return UUID.randomUUID();
  }

  public static UUID parse(String uuid) {
    return UUID.fromString(uuid);
  }

  private UuidManager() {
    throw new UnsupportedOperationException("Utility class");
  }
}
