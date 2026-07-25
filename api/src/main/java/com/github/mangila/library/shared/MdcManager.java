package com.github.mangila.library.shared;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import org.jboss.logging.MDC;

@ApplicationScoped
public class MdcManager {

  public static final String TRACE_ID_HEADER_KEY = "x-trace-id";
  private static final String TRACE_ID_MDC_KEY = "traceId";

  private final UuidFactory uuidFactory;

  public MdcManager(UuidFactory uuidFactory) {
    this.uuidFactory = uuidFactory;
  }

  public Object getTraceId() {
    return MDC.get(TRACE_ID_MDC_KEY);
  }

  public void initializeTraceId() {
    final UUID traceId = uuidFactory.generate();
    MDC.put(TRACE_ID_MDC_KEY, traceId.toString());
  }

  public void removeTraceId() {
    MDC.remove(TRACE_ID_MDC_KEY);
  }
}
