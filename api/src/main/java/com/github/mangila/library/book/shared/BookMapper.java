package com.github.mangila.library.book.shared;

import com.github.mangila.library.book.data.BookEntity;
import com.github.mangila.library.book.domain.Book;
import com.github.mangila.library.book.graphql.BookGraphqlDto;
import com.github.mangila.library.book.grpc.generated.BookRpcDto;
import com.github.mangila.library.book.rest.BookRestDto;
import com.github.mangila.library.shared.JsonMapper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookMapper {

  private final JsonMapper jsonMapper;

  public BookMapper(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  public Book toDomain(BookEntity bookEntity) {
    return new Book(
        bookEntity.getId(),
        bookEntity.getAuthorId(),
        bookEntity.getTitle(),
        bookEntity.getCategory(),
        bookEntity.getPublicationDate(),
        bookEntity.getDescription(),
        bookEntity.getMetadata());
  }

  public BookEntity toEntity(Book book) {
    final BookEntity entity = new BookEntity();
    entity.setId(book.id());
    entity.setAuthorId(book.authorId());
    entity.setTitle(book.title());
    entity.setCategory(book.category());
    entity.setPublicationDate(book.publicationDate());
    entity.setDescription(book.description());
    entity.setMetadata(book.metadata());
    return entity;
  }

  public BookGraphqlDto toGraphqlDto(Book book) {
    return new BookGraphqlDto(book.id().toString());
  }

  public BookRestDto toRestDto(Book book) {
    return new BookRestDto(
        book.id().toString(),
        book.authorId().toString(),
        book.title(),
        book.category(),
        book.publicationDate(),
        book.description(),
        book.metadata());
  }

  public BookRpcDto toRpcDto(Book book) {
    String metadata = jsonMapper.toJson(book.metadata());
    return BookRpcDto.newBuilder()
        .setId(book.id().toString())
        .setAuthorId(book.authorId().toString())
        .setTitle(book.title())
        .setCategory(book.category().name())
        .setPublicationDate(book.publicationDate().toString())
        .setDescription(book.description())
        .setMetadata(metadata)
        .build();
  }
}
