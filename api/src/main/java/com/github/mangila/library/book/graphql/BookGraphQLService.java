package com.github.mangila.library.book.graphql;

import com.github.mangila.library.book.domain.BookService;
import com.github.mangila.library.book.shared.BookMapper;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class BookGraphQLService {

  private final BookService bookService;
  private final BookMapper bookMapper;
  private final UuidFactory uuidFactory;

  public BookGraphQLService(
      BookService bookService, BookMapper bookMapper, UuidFactory uuidFactory) {
    this.bookService = bookService;
    this.bookMapper = bookMapper;
    this.uuidFactory = uuidFactory;
  }

  public BookGraphqlDto findById(@UUID String id) {
    final java.util.UUID uuid = uuidFactory.parse(id);
    return bookService.findByIdOptional(uuid).map(bookMapper::toGraphqlDto).orElseThrow();
  }
}
