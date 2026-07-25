package com.github.mangila.library.author.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorToolResource {

  private final AuthorMcpService authorMcpService;

  public AuthorToolResource(AuthorMcpService authorMcpService) {
    this.authorMcpService = authorMcpService;
  }

  @Tool(description = "Find author by ID")
  @Authenticated
  @RunOnVirtualThread
  public AuthorMcpDto findById(
      @ToolArg(description = "UUID of the author's primary key") String id) {
    return authorMcpService.findById(id);
  }
}
