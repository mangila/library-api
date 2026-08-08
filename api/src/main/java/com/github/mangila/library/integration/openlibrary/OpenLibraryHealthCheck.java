package com.github.mangila.library.integration.openlibrary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Readiness
@ApplicationScoped
public class OpenLibraryHealthCheck implements HealthCheck {

  private final OpenLibraryClient openLibraryClient;

  public OpenLibraryHealthCheck(@RestClient OpenLibraryClient openLibraryClient) {
    this.openLibraryClient = openLibraryClient;
  }

  @Override
  public HealthCheckResponse call() {
    try (Response response = openLibraryClient.ping()) {
      boolean ready = response.getStatus() == 200;
      return HealthCheckResponse.named("openlibrary").status(ready).build();
    }
  }
}
