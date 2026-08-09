package com.github.mangila.library.author.shared;

import com.github.mangila.library.author.data.AuthorEntity;
import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.grpc.generated.AuthorRpcDto;
import com.github.mangila.library.author.rest.AuthorWebDto;
import com.github.mangila.library.shared.JsonMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.csv.CSVRecord;

@ApplicationScoped
public class AuthorMapper {

  private final JsonMapper jsonMapper;

  public AuthorMapper(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  public Author toDomain(AuthorEntity authorEntity) {
    final List<UUID> books = new ArrayList<>(authorEntity.getBooks());
    return new Author(authorEntity.getId(), authorEntity.getName(), books);
  }

  public Author toDomain(CSVRecord csvRecord) {
    Map<String, Object> json = jsonMapper.fromJsonObject(csvRecord.get("JSON"));
    return new Author(UUID.randomUUID(), json.get("name").toString(), new ArrayList<>());
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
