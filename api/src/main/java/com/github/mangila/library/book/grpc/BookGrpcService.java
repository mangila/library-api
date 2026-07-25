package com.github.mangila.library.book.grpc;

import com.github.mangila.library.book.domain.BookService;
import com.github.mangila.library.book.shared.BookMapper;
import com.github.mangila.library.shared.GrpcProblemUtil;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;

@ApplicationScoped
public class BookGrpcService {

  private final UuidFactory uuidFactory;
  private final BookService bookService;
  private final BookMapper bookMapper;

  public BookGrpcService(UuidFactory uuidFactory, BookService bookService, BookMapper bookMapper) {
    this.uuidFactory = uuidFactory;
    this.bookService = bookService;
    this.bookMapper = bookMapper;
  }

  public BookRpcDto findById(@NotNull @UUID String id) {
    final java.util.UUID uuid = uuidFactory.parse(id);
    return bookService
        .findByIdOptional(uuid)
        .map(bookMapper::toRpcDto)
        .orElseThrow(() -> GrpcProblemUtil.notFound("Book not found with id: %s".formatted(id)));
  }
}
