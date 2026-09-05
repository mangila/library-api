package com.github.mangila.library.author.mcp;

import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.UuidFactory;
import io.quarkiverse.mcp.server.ToolCallException;
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
    if (!uuidFactory.isUuid(id)) {
      throw new ToolCallException("Not valid UUID: " + id);
    }
    final UUID uuid = uuidFactory.parse(id);
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toMcpDto)
        .orElseThrow(() -> new ToolCallException("Author not found: " + id));
  }
}
