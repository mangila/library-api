package com.github.mangila.library.shared;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UuidFactory {

  public UUID generate() {
    return UUID.randomUUID();
  }

  public UUID parse(String uuid) {
    return UUID.fromString(uuid);
  }
}
