package com.github.mangila.library.author.domain;

import com.github.mangila.library.author.data.AuthorDataService;
import com.github.mangila.library.author.data.AuthorEntity;
import com.github.mangila.library.author.shared.AuthorMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AuthorService {

  private final AuthorDataService authorDataService;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorDataService authorDataService, AuthorMapper authorMapper) {
    this.authorDataService = authorDataService;
    this.authorMapper = authorMapper;
  }

  public Optional<Author> findByIdOptional(UUID id) {
    return authorDataService.findByIdOptional(id).map(authorMapper::toDomain);
  }

  public void saveAll(List<Author> authors) {
    final List<AuthorEntity> authorEntities = authors.stream().map(authorMapper::toEntity).toList();
    authorDataService.saveAll(authorEntities);
  }
}
