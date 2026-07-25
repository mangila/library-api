package com.github.mangila.library.integration.openlibrary;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@RegisterRestClient(configKey = "openlibrary")
public interface OpenLibraryClient {

  @GET
  @Path("/data/{fileName}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  RestResponse<InputStream> download(
      @PathParam("fileName") String fileName, @HeaderParam("Range") String rangeHeader);

  @HEAD
  @Path("/data/{fileName}")
  Response metadata(@PathParam("fileName") String fileName);

  @HEAD
  Response ping();
}
