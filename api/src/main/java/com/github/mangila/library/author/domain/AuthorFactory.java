package com.github.mangila.library.author.domain;

import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AuthorFactory {

  private final UuidFactory uuidFactory;

  public AuthorFactory(UuidFactory uuidFactory) {
    this.uuidFactory = uuidFactory;
  }

  public Author create(String name, List<UUID> books) {
    final UUID id = uuidFactory.generate();
    return new Author(id, name, books);
  }
}
