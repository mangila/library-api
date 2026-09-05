package com.github.mangila.library.shared;

import io.github.mangila.ensure4j.Ensure;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UuidFactory {

  public UUID generate() {
    return UUID.randomUUID();
  }

  public boolean isUuid(String s) {
    try {
      Ensure.notBlank(s);
      parse(s);
      return true;
    } catch (Exception _) {
      return false;
    }
  }

  public UUID parse(String uuid) {
    return UUID.fromString(uuid);
  }
}
