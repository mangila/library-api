package com.github.mangila.library.book.web;

import com.github.mangila.library.book.domain.BookService;
import com.github.mangila.library.book.shared.BookMapper;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@ApplicationScoped
public class BookRestService {

  private final BookService bookService;
  private final BookMapper bookMapper;
  private final UuidFactory uuidFactory;

  public BookRestService(BookService bookService, BookMapper bookMapper, UuidFactory uuidFactory) {
    this.bookService = bookService;
    this.bookMapper = bookMapper;
    this.uuidFactory = uuidFactory;
  }

  public BookRestDto findById(@NotNull String id) {
    final UUID uuid = uuidFactory.parse(id);
    return bookService
        .findByIdOptional(uuid)
        .map(bookMapper::toRestDto)
        .orElseThrow(
            () -> HttpProblemException.notFound("Book not found with id: %s".formatted(id)));
  }
}
