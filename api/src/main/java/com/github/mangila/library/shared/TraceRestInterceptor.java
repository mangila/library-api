package com.github.mangila.library.shared;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;

@Provider
public class TraceRestInterceptor {

  private final MdcManager mdcManager;

  public TraceRestInterceptor(MdcManager mdcManager) {
    this.mdcManager = mdcManager;
  }

  @ServerRequestFilter(preMatching = true)
  public void filterRequest(ContainerRequestContext requestContext) {
    mdcManager.initializeTraceId();
  }

  @ServerResponseFilter
  public void filterResponse(
      ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    try {
      final var responseContextHeaders = responseContext.getHeaders();
      final Object traceId = mdcManager.getTraceId();
      responseContextHeaders.add(MdcManager.TRACE_ID_HEADER_KEY, traceId);
    } finally {
      mdcManager.removeTraceId();
    }
  }
}
