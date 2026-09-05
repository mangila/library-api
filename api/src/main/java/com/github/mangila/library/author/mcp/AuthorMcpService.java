package com.github.mangila.library.author.mcp;

import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AuthorMcpService {

  private final AuthorService authorService;
  private final AuthorMapper authorMapper;
  private final UuidFactory uuidFactory;

  public AuthorMcpService(
      AuthorService authorService, AuthorMapper authorMapper, UuidFactory uuidFactory) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
    this.uuidFactory = uuidFactory;
  }

  public AuthorMcpDto findById(String id) {
    final UUID uuid = uuidFactory.parse(id);
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toMcpDto)
        .orElseThrow(() -> HttpProblemException.notFound(id));
  }
}
