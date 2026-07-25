package com.github.mangila.library.shared;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UuidProvider {

  public UUID generate() {
    return UuidManager.generate();
  }

  public UUID parse(String uuid) {
    return UuidManager.parse(uuid);
  }
}
