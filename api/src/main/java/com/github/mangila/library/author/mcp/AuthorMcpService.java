package com.github.mangila.library.author.mcp;

import com.github.mangila.library.author.application.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.UuidProvider;
import io.quarkiverse.mcp.server.ToolCallException;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AuthorMcpService {

  private final AuthorService authorService;
  private final AuthorMapper authorMapper;
  private final UuidProvider uuidProvider;

  public AuthorMcpService(
      AuthorService authorService, AuthorMapper authorMapper, UuidProvider uuidProvider) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
    this.uuidProvider = uuidProvider;
  }

  public AuthorMcpDto findByIdOrThrow(String id) {
    final UUID uuid;
    try {
      uuid = uuidProvider.parse(id);
    } catch (IllegalArgumentException e) {
      throw new ToolCallException("Not valid UUID: %s".formatted(id), e);
    }
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toMcpDto)
        .orElseThrow(() -> new ToolCallException("Author not found: %s".formatted(id)));
  }
}
