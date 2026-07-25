package com.github.mangila.library.author.domain;

import java.util.List;
import java.util.UUID;

public record Author(UUID id, String name, List<UUID> books) {

  public void addBook(UUID id) {
    books.add(id);
  }
}
