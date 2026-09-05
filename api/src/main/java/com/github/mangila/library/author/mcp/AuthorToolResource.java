package com.github.mangila.library.author.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class AuthorToolResource {

  private final AuthorMcpService authorMcpService;

  public AuthorToolResource(AuthorMcpService authorMcpService) {
    this.authorMcpService = authorMcpService;
  }

  @Tool(description = "Find author by ID")
  public AuthorMcpDto findById(
      @ToolArg(description = "UUID of the author's primary key") @UUID String id) {
    return authorMcpService.findById(id);
  }
}
