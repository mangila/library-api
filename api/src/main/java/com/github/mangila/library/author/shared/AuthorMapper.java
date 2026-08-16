package com.github.mangila.library.author.shared;

import com.github.mangila.library.author.data.AuthorEntity;
import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.graphql.AuthorGraphqlDto;
import com.github.mangila.library.author.grpc.generated.AuthorRpcDto;
import com.github.mangila.library.author.rest.AuthorRestDto;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.function.Consumer;

@ApplicationScoped
public class AuthorMapper {

  private static <T> void applyIfNotNull(T value, Consumer<T> action) {
    if (value != null) {
      action.accept(value);
    }
  }

  public Author toDomain(AuthorEntity authorEntity) {
    return new Author(
        authorEntity.getId(),
        authorEntity.getOpenLibraryKey(),
        authorEntity.getName(),
        authorEntity.getPersonalName(),
        authorEntity.getAlternateNames(),
        authorEntity.getUris(),
        authorEntity.getBio(),
        authorEntity.getLocation(),
        authorEntity.getBirthDate(),
        authorEntity.getDeathDate(),
        authorEntity.getWikipedia(),
        authorEntity.getLinks(),
        authorEntity.getBooks(),
        authorEntity.getWorks());
  }

  public AuthorEntity toEntity(Author author) {
    final AuthorEntity entity = new AuthorEntity();
    entity.setId(author.id());
    entity.setOpenLibraryKey(author.openLibraryKey());
    entity.setName(author.name());
    entity.setPersonalName(author.personalName());
    applyIfNotNull(author.alternateNames(), entity.getAlternateNames()::addAll);
    applyIfNotNull(author.uris(), entity.getUris()::addAll);
    entity.setBio(author.bio());
    entity.setLocation(author.location());
    entity.setBirthDate(author.birthDate());
    entity.setDeathDate(author.deathDate());
    entity.setWikipedia(author.wikipedia());
    applyIfNotNull(author.links(), entity.getLinks()::addAll);
    applyIfNotNull(author.books(), entity.getBooks()::addAll);
    applyIfNotNull(author.works(), entity.getWorks()::addAll);
    return entity;
  }

  public AuthorGraphqlDto toGraphqlDto(Author author) {
    return new AuthorGraphqlDto(
        author.id(),
        author.openLibraryKey(),
        author.name(),
        author.personalName(),
        author.alternateNames(),
        author.uris(),
        author.bio(),
        author.location(),
        author.birthDate(),
        author.deathDate(),
        author.wikipedia(),
        author.links(),
        author.books(),
        author.works());
  }

  public AuthorRpcDto toRpcDto(Author author) {
    if (author == null) {
      return AuthorRpcDto.getDefaultInstance();
    }
    final AuthorRpcDto.Builder builder = AuthorRpcDto.newBuilder();
    applyIfNotNull(author.id(), id -> builder.setId(id.toString()));
    applyIfNotNull(author.openLibraryKey(), builder::setOpenLibraryKey);
    applyIfNotNull(author.name(), builder::setName);
    applyIfNotNull(author.personalName(), builder::setPersonalName);
    applyIfNotNull(author.alternateNames(), builder::addAllAlternateNames);
    applyIfNotNull(author.uris(), builder::addAllUris);
    applyIfNotNull(author.bio(), builder::setBio);
    applyIfNotNull(author.location(), builder::setLocation);
    applyIfNotNull(author.birthDate(), builder::setBirthDate);
    applyIfNotNull(author.deathDate(), builder::setDeathDate);
    applyIfNotNull(author.wikipedia(), builder::setWikipedia);
    applyIfNotNull(author.links(), builder::addAllLinks);
    applyIfNotNull(author.books(), builder::addAllBooks);
    applyIfNotNull(author.works(), builder::addAllWorks);
    return builder.build();
  }

  public AuthorRestDto toWebDto(Author author) {
    return new AuthorRestDto(
        author.id().toString(),
        author.openLibraryKey(),
        author.name(),
        author.personalName(),
        author.alternateNames(),
        author.uris(),
        author.bio(),
        author.location(),
        author.birthDate(),
        author.deathDate(),
        author.wikipedia(),
        author.links(),
        author.books(),
        author.works());
  }
}
