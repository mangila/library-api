package com.github.mangila.library.book.domain;

import com.github.mangila.library.book.data.Category;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public record Book(
    UUID id,
    UUID authorId,
    String title,
    Category category,
    LocalDate publicationDate,
    String description,
    Map<String, Object> metadata) {

  public boolean loan() {
    return Boolean.TRUE.equals(metadata.get("loan"));
  }
}
