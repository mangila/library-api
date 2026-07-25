package com.github.mangila.library.author.data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AuthorDataService {

  private final AuthorRepository authorRepository;

  public AuthorDataService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Transactional
  public Optional<AuthorEntity> findByIdOptional(UUID id) {
    return authorRepository.findByIdOptional(id);
  }

  @Transactional
  public void saveAll(List<AuthorEntity> authors) {
    authorRepository.persist(authors);
  }
}
