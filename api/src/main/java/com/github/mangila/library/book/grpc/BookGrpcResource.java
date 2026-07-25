package com.github.mangila.library.book.grpc;

import com.github.mangila.library.shared.GrpcProblemUtil;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;

@GrpcService
public class BookGrpcResource implements BookGrpc {

  private final BookGrpcService bookGrpcService;

  public BookGrpcResource(BookGrpcService bookGrpcService) {
    this.bookGrpcService = bookGrpcService;
  }

  @Override
  @RunOnVirtualThread
  public Uni<BookRpcDto> findById(FindByIdRequest request) {
    return Uni.createFrom()
        .item(() -> bookGrpcService.findById(request.getId()))
        .onFailure()
        .transform(GrpcProblemUtil::transformOnFailure);
  }
}
