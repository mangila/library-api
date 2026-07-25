package com.github.mangila.library.author.application;

import com.github.mangila.library.integration.openlibrary.OpenLibraryAuthor;
import com.github.mangila.library.shared.*;
import com.github.mangila.library.staging.StagingEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class AuthorFactory {

  private final UuidProvider uuidProvider;
  private final JsonMapper jsonMapper;

  public AuthorFactory(UuidProvider uuidProvider, JsonMapper jsonMapper) {
    this.uuidProvider = uuidProvider;
    this.jsonMapper = jsonMapper;
  }

  public Author from(StagingEntity stagingEntity) {
    final UUID id = uuidProvider.generate();
    final String openLibraryKey = stagingEntity.getKey();
    final Map<String, Object> json = stagingEntity.getJson();
    final OpenLibraryAuthor author = jsonMapper.toObject(json, OpenLibraryAuthor.class);
    final StringCollection alternateNames = new StringCollection(author.alternateNames());
    final UriCollection uris = UriCollection.from(author.uris());
    final UriCollection links = extractLinks(author.links());
    return new Author(
        id,
        openLibraryKey,
        author.name(),
        author.personalName(),
        alternateNames,
        uris,
        author.getBioText(),
        author.location(),
        author.birthDate(),
        author.deathDate(),
        author.wikipedia(),
        links,
        StringCollection.EMPTY,
        StringCollection.EMPTY,
        json);
  }

  private UriCollection extractLinks(List<OpenLibraryAuthor.OpenLibraryLink> openLibraryLinks) {
    if (openLibraryLinks == null) {
      return UriCollection.EMPTY;
    }
    final List<String> l =
        openLibraryLinks.stream().map(OpenLibraryAuthor.OpenLibraryLink::url).toList();
    return UriCollection.from(l);
  }
}
