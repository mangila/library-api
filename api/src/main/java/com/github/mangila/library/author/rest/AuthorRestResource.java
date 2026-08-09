package com.github.mangila.library.author.rest;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.UUID;
import org.jboss.resteasy.reactive.RestResponse;

@Path("api/v1/authors")
public class AuthorRestResource {

  private final AuthorRestService authorRestService;

  public AuthorRestResource(AuthorRestService authorRestService) {
    this.authorRestService = authorRestService;
  }

  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<AuthorRestDto> findById(@PathParam("id") @UUID String id) {
    final AuthorRestDto dto = authorRestService.findById(id);
    return RestResponse.ok(dto);
  }
}
