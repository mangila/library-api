package com.github.mangila.library.shared;

import io.grpc.ForwardingServerCall;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.quarkus.grpc.GlobalInterceptor;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@GlobalInterceptor
public class TraceGrpcInterceptor implements ServerInterceptor {

  private static final Metadata.Key<String> TRACE_ID_HEADER =
      Metadata.Key.of(MdcManager.TRACE_ID_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);

  private final MdcManager mdcManager;

  public TraceGrpcInterceptor(MdcManager mdcManager) {
    this.mdcManager = mdcManager;
  }

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    mdcManager.initializeTraceId();
    final var tracedCall =
        new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
          @Override
          public void sendHeaders(Metadata responseHeaders) {
            responseHeaders.put(TRACE_ID_HEADER, mdcManager.getTraceId().toString());
            super.sendHeaders(responseHeaders);
          }
        };
    final var listener = next.startCall(tracedCall, headers);
    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
      @Override
      public void onCancel() {
        try {
          super.onCancel();
        } finally {
          mdcManager.removeTraceId();
        }
      }

      @Override
      public void onComplete() {
        try {
          super.onComplete();
        } finally {
          mdcManager.removeTraceId();
        }
      }
    };
  }
}
