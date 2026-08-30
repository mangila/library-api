package com.github.mangila.library.shared;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.UUID;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;

@ApplicationScoped
public class TraceRestInterceptor {

  private final UuidFactory uuidFactory;

  public TraceRestInterceptor(UuidFactory uuidFactory) {
    this.uuidFactory = uuidFactory;
  }

  @ServerRequestFilter(preMatching = true)
  public void filterRequest(ContainerRequestContext requestContext) {
    final UUID traceId = uuidFactory.generate();
    MdcManager.setTraceId(traceId);
    requestContext.setProperty("traceId", traceId.toString());
  }

  @ServerResponseFilter
  public void filterResponse(
      ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    try {
      final Object traceId = requestContext.getProperty("traceId");
      final MultivaluedMap<String, Object> responseContextHeaders = responseContext.getHeaders();
      responseContextHeaders.add(MdcManager.TRACE_ID_HEADER_KEY, traceId);
    } finally {
      MdcManager.removeTraceId();
    }
  }
}
