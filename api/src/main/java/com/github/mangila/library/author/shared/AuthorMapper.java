package com.github.mangila.library.author.shared;

import com.github.mangila.library.author.data.AuthorEntity;
import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.graphql.AuthorGraphqlDto;
import com.github.mangila.library.author.grpc.generated.AuthorRpcDto;
import com.github.mangila.library.author.rest.AuthorRestDto;
import com.github.mangila.library.shared.StringCollection;
import com.github.mangila.library.shared.UriCollection;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorMapper {

  public Author toDomain(AuthorEntity authorEntity) {
    final StringCollection alternateNames = new StringCollection(authorEntity.getAlternateNames());
    final UriCollection uris = UriCollection.from(authorEntity.getUris());
    final UriCollection links = UriCollection.from(authorEntity.getLinks());
    final StringCollection books = new StringCollection(authorEntity.getBooks());
    final StringCollection works = new StringCollection(authorEntity.getWorks());
    return new Author(
        authorEntity.getId(),
        authorEntity.getOpenLibraryKey(),
        authorEntity.getName(),
        authorEntity.getPersonalName(),
        alternateNames,
        uris,
        authorEntity.getBio(),
        authorEntity.getLocation(),
        authorEntity.getBirthDate(),
        authorEntity.getDeathDate(),
        authorEntity.getWikipedia(),
        links,
        books,
        works,
        authorEntity.getOpenLibraryJson());
  }

  public AuthorEntity toEntity(Author author) {
    final AuthorEntity entity = new AuthorEntity();
    entity.setId(author.id());
    entity.setOpenLibraryKey(author.openLibraryKey());
    entity.setName(author.name());
    entity.setPersonalName(author.personalName());
    entity.setAlternateNames(author.alternateNames().value());
    entity.setUris(author.uris().asStringList());
    entity.setBio(author.bio());
    entity.setLocation(author.location());
    entity.setBirthDate(author.birthDate());
    entity.setDeathDate(author.deathDate());
    entity.setWikipedia(author.wikipedia());
    entity.setLinks(author.links().asStringList());
    entity.setBooks(author.books().value());
    entity.setWorks(author.works().value());
    entity.setOpenLibraryJson(author.originalJson());
    return entity;
  }

  public AuthorGraphqlDto toGraphqlDto(Author author) {
    return new AuthorGraphqlDto(
        author.id(),
        author.openLibraryKey(),
        author.name(),
        author.personalName(),
        author.alternateNames().value(),
        author.uris().asStringList(),
        author.bio(),
        author.location(),
        author.birthDate(),
        author.deathDate(),
        author.wikipedia(),
        author.links().asStringList(),
        author.books().value(),
        author.works().value());
  }

  public AuthorRpcDto toRpcDto(Author author) {
    final String bio = author.bio();
    final String location = author.location();
    final String birthDate = author.birthDate();
    final String deathDate = author.deathDate();
    final String wikipedia = author.wikipedia();
    final AuthorRpcDto.Builder builder =
        AuthorRpcDto.newBuilder()
            .setId(author.id().toString())
            .setOpenLibraryKey(author.openLibraryKey())
            .setName(author.name())
            .setPersonalName(author.personalName())
            .addAllAlternateNames(author.alternateNames().value())
            .addAllUris(author.uris().asStringList())
            .addAllLinks(author.links().asStringList())
            .addAllBooks(author.books().value())
            .addAllWorks(author.works().value());
    if (bio != null) {
      builder.setBio(bio);
    }
    if (location != null) {
      builder.setLocation(location);
    }
    if (birthDate != null) {
      builder.setBirthDate(birthDate);
    }
    if (deathDate != null) {
      builder.setDeathDate(deathDate);
    }
    if (wikipedia != null) {
      builder.setWikipedia(wikipedia);
    }
    return builder.build();
  }

  public AuthorRestDto toWebDto(Author author) {
    return new AuthorRestDto(
        author.id().toString(),
        author.openLibraryKey(),
        author.name(),
        author.personalName(),
        author.alternateNames().value(),
        author.uris().asStringList(),
        author.bio(),
        author.location(),
        author.birthDate(),
        author.deathDate(),
        author.wikipedia(),
        author.links().asStringList(),
        author.books().value(),
        author.works().value());
  }
}
