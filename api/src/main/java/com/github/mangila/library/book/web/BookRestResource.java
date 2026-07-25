package com.github.mangila.library.book.web;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.UUID;
import org.jboss.resteasy.reactive.RestResponse;

@Path("api/v1/books")
public class BookRestResource {

  private final BookRestService bookRestService;

  public BookRestResource(BookRestService bookRestService) {
    this.bookRestService = bookRestService;
  }

  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<BookRestDto> findById(@PathParam("id") @UUID String id) {
    final BookRestDto dto = bookRestService.findById(id);
    return RestResponse.ok(dto);
  }
}
