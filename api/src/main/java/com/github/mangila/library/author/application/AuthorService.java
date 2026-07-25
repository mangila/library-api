package com.github.mangila.library.author.application;

import com.github.mangila.library.author.data.AuthorEntity;
import com.github.mangila.library.author.data.AuthorRepository;
import com.github.mangila.library.author.shared.AuthorMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
    this.authorMapper = authorMapper;
  }

  public Optional<Author> findByIdOptional(UUID id) {
    return authorRepository.findByIdOptional(id).map(authorMapper::toDomain);
  }

  public void saveAll(List<Author> authors) {
    final List<AuthorEntity> authorEntities = authors.stream().map(authorMapper::toEntity).toList();
    authorRepository.persist(authorEntities);
  }
}
