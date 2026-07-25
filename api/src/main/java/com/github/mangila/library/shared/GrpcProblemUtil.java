package com.github.mangila.library.shared;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import io.quarkus.logging.Log;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public final class GrpcProblemUtil {

  public static StatusRuntimeException notFound(String message) {
    return new StatusRuntimeException(Status.NOT_FOUND.withDescription(message));
  }

  public static StatusRuntimeException transformOnFailure(Throwable throwable) {
    if (throwable instanceof StatusRuntimeException || throwable instanceof StatusException) {
      return Status.fromThrowable(throwable).asRuntimeException();
    }
    if (throwable instanceof ConstraintViolationException cve) {
      String summary =
          cve.getConstraintViolations().stream()
              .map(ConstraintViolation::getMessage)
              .collect(Collectors.joining(", "));
      return Status.INVALID_ARGUMENT.withDescription(summary).asRuntimeException();
    }
    Log.error("An unexpected internal server error occurred.", throwable);
    return Status.INTERNAL
        .withDescription("An unexpected internal server error occurred.")
        .asRuntimeException();
  }

  private GrpcProblemUtil() {
    throw new UnsupportedOperationException("This utility class should not be instantiated");
  }
}
