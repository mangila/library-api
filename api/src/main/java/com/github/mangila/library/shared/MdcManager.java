package com.github.mangila.library.shared;

import java.util.UUID;
import org.jboss.logging.MDC;

public final class MdcManager {

  public static final String TRACE_ID_HEADER_KEY = "x-trace-id";
  private static final String TRACE_ID_MDC_KEY = "traceId";

  public static String getTraceId() {
    return (String) MDC.get(TRACE_ID_MDC_KEY);
  }

  public static void removeTraceId() {
    MDC.remove(TRACE_ID_MDC_KEY);
  }

  public static void setTraceId(UUID traceId) {
    MDC.put(TRACE_ID_MDC_KEY, traceId.toString());
  }

  private MdcManager() {
    throw new UnsupportedOperationException("Utility class");
  }
}
