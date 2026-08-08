package com.github.mangila.library.author.grpc;

import com.github.mangila.library.author.grpc.generated.AuthorGrpc;
import com.github.mangila.library.author.grpc.generated.AuthorRpcDto;
import com.github.mangila.library.author.grpc.generated.FindByIdRequest;
import com.github.mangila.library.shared.GrpcProblemUtil;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

@GrpcService
public class AuthorGrpcResource implements AuthorGrpc {

  private final AuthorGrpcService authorGrpcService;

  @Inject
  public AuthorGrpcResource(AuthorGrpcService authorGrpcService) {
    this.authorGrpcService = authorGrpcService;
  }

  @Override
  @RunOnVirtualThread
  public Uni<AuthorRpcDto> findById(FindByIdRequest request) {
    return Uni.createFrom()
        .item(() -> authorGrpcService.findById(request.getId()))
        .onFailure()
        .transform(GrpcProblemUtil::transformOnFailure);
  }
}
