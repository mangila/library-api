package com.github.mangila.library.book.domain;

import com.github.mangila.library.book.data.BookDataService;
import com.github.mangila.library.book.data.BookEntity;
import com.github.mangila.library.book.shared.BookMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BookService {

  private final BookDataService bookDataService;
  private final BookMapper bookMapper;

  public BookService(BookDataService bookDataService, BookMapper bookMapper) {
    this.bookDataService = bookDataService;
    this.bookMapper = bookMapper;
  }

  public Optional<Book> findByIdOptional(UUID id) {
    return bookDataService.findByIdOptional(id).map(bookMapper::toDomain);
  }

  public void saveAll(List<Book> books) {
    final List<BookEntity> bookEntities = books.stream().map(bookMapper::toEntity).toList();
    bookDataService.saveAll(bookEntities);
  }
}
