package com.github.mangila.library.author.graphql;

import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class AuthorGraphQlService {

  private final AuthorService authorService;
  private final AuthorMapper authorMapper;
  private final UuidFactory uuidFactory;

  public AuthorGraphQlService(
      AuthorService authorService, AuthorMapper authorMapper, UuidFactory uuidFactory) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
    this.uuidFactory = uuidFactory;
  }

  public AuthorGraphqlDto findById(@UUID String id) {
    final java.util.UUID uuid = uuidFactory.parse(id);
    return authorService.findByIdOptional(uuid).map(authorMapper::toGraphqlDto).orElseThrow();
  }
}
