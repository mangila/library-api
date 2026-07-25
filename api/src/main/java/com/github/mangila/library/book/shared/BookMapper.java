package com.github.mangila.library.book.shared;

import com.github.mangila.library.book.data.BookEntity;
import com.github.mangila.library.book.data.Category;
import com.github.mangila.library.book.domain.Book;
import com.github.mangila.library.book.graphql.BookGraphqlDto;
import com.github.mangila.library.book.grpc.BookRpcDto;
import com.github.mangila.library.book.web.BookRestDto;
import com.github.mangila.library.shared.JsonMapper;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.csv.CSVRecord;

@ApplicationScoped
public class BookMapper {

  private final UuidFactory uuidFactory;
  private final JsonMapper jsonMapper;

  public BookMapper(UuidFactory uuidFactory, JsonMapper jsonMapper) {
    this.uuidFactory = uuidFactory;
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

  public Book toDomain(CSVRecord csvRecord, UUID authorId) {
    final UUID id = uuidFactory.parse(csvRecord.get("id"));
    final String title = csvRecord.get("title");
    final Category category = Category.valueOf(csvRecord.get("category").toUpperCase(Locale.ROOT));
    final LocalDate publicationDdate = LocalDate.parse(csvRecord.get("publicationDate"));
    final String description = csvRecord.get("description");
    final Map<String, Object> metadata = jsonMapper.fromJsonObject(csvRecord.get("metadata"));
    return new Book(id, authorId, title, category, publicationDdate, description, metadata);
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
