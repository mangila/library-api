package com.github.mangila.library.author.rest;

import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@ApplicationScoped
public class AuthorRestService {

  private final AuthorMapper authorMapper;
  private final AuthorService authorService;
  private final UuidFactory uuidFactory;

  public AuthorRestService(
      AuthorService authorService, AuthorMapper authorMapper, UuidFactory uuidFactory) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
    this.uuidFactory = uuidFactory;
  }

  public AuthorRestDto findById(@NotNull String id) {
    final UUID uuid = uuidFactory.parse(id);
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toWebDto)
        .orElseThrow(() -> HttpProblemException.notFound(id));
  }
}
