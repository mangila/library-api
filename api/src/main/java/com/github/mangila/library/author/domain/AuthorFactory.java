package com.github.mangila.library.author.domain;

import com.github.mangila.library.integration.openlibrary.model.OpenLibraryAuthor;
import com.github.mangila.library.shared.JsonMapper;
import com.github.mangila.library.shared.StringCollection;
import com.github.mangila.library.shared.UriCollection;
import com.github.mangila.library.shared.UuidFactory;
import com.github.mangila.library.staging.StagingEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class AuthorFactory {

  private final UuidFactory uuidFactory;
  private final JsonMapper jsonMapper;

  public AuthorFactory(UuidFactory uuidFactory, JsonMapper jsonMapper) {
    this.uuidFactory = uuidFactory;
    this.jsonMapper = jsonMapper;
  }

  public Author from(StagingEntity stagingEntity) {
    final UUID id = uuidFactory.generate();
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
