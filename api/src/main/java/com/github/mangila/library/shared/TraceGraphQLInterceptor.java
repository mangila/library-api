package com.github.mangila.library.shared;

import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.cdi.event.AfterExecute;
import io.smallrye.graphql.cdi.event.BeforeExecute;
import io.smallrye.graphql.cdi.event.ErrorExecute;
import io.smallrye.graphql.cdi.event.ErrorInfo;
import io.smallrye.graphql.execution.context.SmallRyeContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.UUID;

@ApplicationScoped
public class TraceGraphQLInterceptor {

  private final UuidProvider uuidProvider;
  private final SmallRyeContext smallRyeContext;

  public TraceGraphQLInterceptor(UuidProvider uuidProvider, SmallRyeContext smallRyeContext) {
    this.uuidProvider = uuidProvider;
    this.smallRyeContext = smallRyeContext;
  }

  public void afterExecute(@Observes @AfterExecute Context context) {
    MdcManager.removeTraceId();
  }

  public void beforeExecute(@Observes @BeforeExecute Context context) {
    final UUID traceId = uuidProvider.generate();
    MdcManager.setTraceId(traceId);
    smallRyeContext.addExtension(MdcManager.TRACE_ID_HEADER_KEY, MdcManager.getTraceId());
  }

  public void errorExecute(@Observes @ErrorExecute ErrorInfo errorInfo) {
    MdcManager.removeTraceId();
  }
}
