package com.github.mangila.library.author.rest;

import com.github.mangila.library.author.application.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidProvider;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AuthorRestService {

  private final AuthorMapper authorMapper;
  private final AuthorService authorService;
  private final UuidProvider uuidProvider;

  public AuthorRestService(
      AuthorService authorService, AuthorMapper authorMapper, UuidProvider uuidProvider) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
    this.uuidProvider = uuidProvider;
  }

  public AuthorRestDto findById(String id) {
    final UUID uuid = uuidProvider.parse(id);
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toRestDto)
        .orElseThrow(() -> HttpProblemException.notFound(id));
  }
}
