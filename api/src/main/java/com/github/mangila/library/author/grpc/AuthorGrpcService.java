package com.github.mangila.library.author.grpc;

import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.GrpcProblemUtil;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class AuthorGrpcService {

  private final UuidFactory uuidFactory;
  private final AuthorService autorService;
  private final AuthorMapper authorMapper;

  public AuthorGrpcService(
      UuidFactory uuidFactory, AuthorService autorService, AuthorMapper authorMapper) {
    this.uuidFactory = uuidFactory;
    this.autorService = autorService;
    this.authorMapper = authorMapper;
  }

  public AuthorRpcDto findById(@NotNull @UUID String id) {
    final java.util.UUID uuid = uuidFactory.parse(id);
    return autorService
        .findByIdOptional(uuid)
        .map(authorMapper::toRpcDto)
        .orElseThrow(() -> GrpcProblemUtil.notFound("Author not found with id: %s".formatted(id)));
  }
}
