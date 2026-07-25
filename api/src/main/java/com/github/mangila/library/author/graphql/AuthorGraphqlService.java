package com.github.mangila.library.author.graphql;

import com.github.mangila.library.author.application.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class AuthorGraphqlService {

  private final AuthorService authorService;
  private final AuthorMapper authorMapper;
  private final UuidProvider uuidProvider;

  public AuthorGraphqlService(
      AuthorService authorService, AuthorMapper authorMapper, UuidProvider uuidProvider) {
    this.authorService = authorService;
    this.authorMapper = authorMapper;
    this.uuidProvider = uuidProvider;
  }

  public AuthorGraphqlDto findById(@NotNull @UUID String id) {
    final java.util.UUID uuid = uuidProvider.parse(id);
    return authorService
        .findByIdOptional(uuid)
        .map(authorMapper::toGraphqlDto)
        .orElseThrow(() -> HttpProblemException.notFound(id));
  }
}
