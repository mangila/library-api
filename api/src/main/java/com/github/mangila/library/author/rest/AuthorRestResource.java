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

  private final AuthorWebService authorWebService;

  public AuthorRestResource(AuthorWebService authorWebService) {
    this.authorWebService = authorWebService;
  }

  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<AuthorWebDto> findById(@PathParam("id") @UUID String id) {
    final AuthorWebDto dto = authorWebService.findById(id);
    return RestResponse.ok(dto);
  }
}
