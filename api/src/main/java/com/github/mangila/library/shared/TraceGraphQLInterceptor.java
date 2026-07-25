package com.github.mangila.library.shared;

import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.cdi.event.AfterExecute;
import io.smallrye.graphql.cdi.event.BeforeExecute;
import io.smallrye.graphql.cdi.event.ErrorExecute;
import io.smallrye.graphql.cdi.event.ErrorInfo;
import io.smallrye.graphql.execution.context.SmallRyeContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class TraceGraphQLInterceptor {

  private final MdcManager mdcManager;
  private final SmallRyeContext smallRyeContext;

  public TraceGraphQLInterceptor(MdcManager mdcManager, SmallRyeContext smallRyeContext) {
    this.mdcManager = mdcManager;
    this.smallRyeContext = smallRyeContext;
  }

  public void afterExecute(@Observes @AfterExecute Context context) {
    mdcManager.removeTraceId();
  }

  public void beforeExecute(@Observes @BeforeExecute Context context) {
    mdcManager.initializeTraceId();
    smallRyeContext.addExtension(MdcManager.TRACE_ID_HEADER_KEY, mdcManager.getTraceId());
  }

  public void errorExecute(@Observes @ErrorExecute ErrorInfo errorInfo) {
    mdcManager.removeTraceId();
  }
}
