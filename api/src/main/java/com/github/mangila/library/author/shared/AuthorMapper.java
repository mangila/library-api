package com.github.mangila.library.author.shared;

import com.github.mangila.library.author.data.AuthorEntity;
import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.grpc.AuthorRpcDto;
import com.github.mangila.library.author.rest.AuthorWebDto;
import com.github.mangila.library.shared.JsonMapper;
import com.github.mangila.library.shared.UuidFactory;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.csv.CSVRecord;

@ApplicationScoped
public class AuthorMapper {

  private final UuidFactory uuidFactory;
  private final JsonMapper jsonMapper;

  public AuthorMapper(UuidFactory uuidFactory, JsonMapper jsonMapper) {
    this.uuidFactory = uuidFactory;
    this.jsonMapper = jsonMapper;
  }

  public Author toDomain(AuthorEntity authorEntity) {
    final List<UUID> books = new ArrayList<>(authorEntity.getBooks());
    return new Author(authorEntity.getId(), authorEntity.getName(), books);
  }

  public Author toDomain(CSVRecord csvRecord) {
    final UUID id = uuidFactory.parse(csvRecord.get("id"));
    final List<UUID> books =
        jsonMapper.fromJsonArray(csvRecord.get("books")).stream().map(uuidFactory::parse).toList();
    return new Author(id, csvRecord.get("name"), new ArrayList<>(books));
  }

  public AuthorEntity toEntity(Author author) {
    final AuthorEntity entity = new AuthorEntity();
    entity.setId(author.id());
    entity.setName(author.name());
    entity.setBooks(author.books());
    return entity;
  }

  public AuthorRpcDto toRpcDto(Author author) {
    final List<String> books = author.books().stream().map(UUID::toString).toList();
    return AuthorRpcDto.newBuilder()
        .setId(author.id().toString())
        .setName(author.name())
        .addAllBooks(books)
        .build();
  }

  public AuthorWebDto toWebDto(Author author) {
    final List<String> books = author.books().stream().map(UUID::toString).toList();
    return new AuthorWebDto(author.id().toString(), author.name(), books);
  }
}
