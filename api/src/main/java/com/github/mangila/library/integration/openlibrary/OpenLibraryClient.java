package com.github.mangila.library.integration.openlibrary;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@RegisterRestClient(configKey = "openlibrary")
public interface OpenLibraryClient {

  @GET
  @Path("/data/{fileName}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  RestResponse<InputStream> downloadDump(@PathParam("fileName") String fileName);
}
