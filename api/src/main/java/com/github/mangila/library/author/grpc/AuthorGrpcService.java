package com.github.mangila.library.author.grpc;

import com.github.mangila.library.author.application.AuthorService;
import com.github.mangila.library.author.grpc.generated.AuthorRpcDto;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.GrpcProblemUtil;
import com.github.mangila.library.shared.UuidProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class AuthorGrpcService {

  private final UuidProvider uuidProvider;
  private final AuthorService authorService;
  private final AuthorMapper authorMapper;

  public AuthorGrpcService(
      UuidProvider uuidProvider, AuthorService authorService, AuthorMapper authorMapper) {
    this.uuidProvider = uuidProvider;
    this.authorService = authorService;
    this.authorMapper = authorMapper;
  }

  public AuthorRpcDto findByIdOrThrow(@NotNull @UUID String id) {
    final java.util.UUID uuid = uuidProvider.parse(id);
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toRpcDto)
        .orElseThrow(() -> GrpcProblemUtil.notFound("Author not found with id: %s".formatted(id)));
  }
}
