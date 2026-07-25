package com.github.mangila.library.shared;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.quarkiverse.httpproblem.ExceptionMapperBase;
import io.quarkiverse.httpproblem.HttpProblem;
import io.quarkiverse.httpproblem.postprocessing.PostProcessorsRegistry;
import io.smallrye.faulttolerance.api.RateLimitException;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.net.URI;
import java.time.Duration;

@Provider
public class RateLimitExceptionMapper extends ExceptionMapperBase<RateLimitException> {

  private static final URI DEFAULT_TYPE = URI.create("about:blank");

  @Inject
  public RateLimitExceptionMapper(PostProcessorsRegistry postProcessorsRegistry) {
    super(postProcessorsRegistry);
  }

  @Override
  protected HttpProblem toProblem(RateLimitException exception) {
    final Duration retryAfter = Duration.ofMillis(exception.getRetryAfterMillis());
    final Response.Status status = Response.Status.TOO_MANY_REQUESTS;
    return HttpProblem.builder()
        .withType(DEFAULT_TYPE)
        .withStatus(status)
        .withTitle("Rate Limit Exceeded")
        .withDetail(status.getReasonPhrase())
        .withHeader(HttpHeaderNames.RETRY_AFTER.toString(), retryAfter.toSeconds())
        .build();
  }
}
