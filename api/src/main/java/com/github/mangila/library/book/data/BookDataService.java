package com.github.mangila.library.book.data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BookDataService {

  private final BookRepository bookRepository;

  public BookDataService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Transactional
  public Optional<BookEntity> findByIdOptional(UUID id) {
    return bookRepository.findByIdOptional(id);
  }

  @Transactional
  public void saveAll(List<BookEntity> books) {
    bookRepository.persist(books);
  }
}
