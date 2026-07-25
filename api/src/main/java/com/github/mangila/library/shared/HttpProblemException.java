package com.github.mangila.library.shared;

import io.quarkiverse.httpproblem.HttpProblem;
import jakarta.ws.rs.core.Response;
import java.net.URI;

public class HttpProblemException extends HttpProblem {

  private static final URI DEFAULT_TYPE = URI.create("about:blank");

  public static HttpProblemException notFound(String message) {
    return new HttpProblemException(message, Response.Status.NOT_FOUND);
  }

  private static Builder defaultMessage(String message, Response.Status status) {
    final String title = status.getReasonPhrase();
    final int statusCode = status.getStatusCode();
    return builder()
        .withType(DEFAULT_TYPE)
        .withTitle(title)
        .withDetail(message)
        .withStatus(statusCode);
  }

  public HttpProblemException(String message, Response.Status status) {
    super(defaultMessage(message, status));
  }
}
