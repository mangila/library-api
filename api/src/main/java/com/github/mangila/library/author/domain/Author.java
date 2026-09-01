package com.github.mangila.library.author.domain;

import com.github.mangila.library.shared.StringCollection;
import com.github.mangila.library.shared.UriCollection;
import io.github.mangila.ensure4j.Ensure;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record Author(
    UUID id,
    String openLibraryKey,
    String name,
    String personalName,
    StringCollection alternateNames,
    UriCollection uris,
    String bio,
    String location,
    String birthDate,
    String deathDate,
    String wikipedia,
    UriCollection links,
    StringCollection books,
    StringCollection works,
    Map<String, Object> originalJson) {

  public Author {
    Ensure.notNull(id, "author id must not be null");
    Ensure.notBlank(openLibraryKey, "openlibrary key must not be blank");
    name = Objects.requireNonNullElse(name, "unspecified");
    personalName = Objects.requireNonNullElse(personalName, "unspecified");
  }
}
