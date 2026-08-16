package com.github.mangila.library.author.domain;

import com.github.mangila.library.integration.openlibrary.model.OpenLibraryAuthor;
import com.github.mangila.library.shared.JsonMapper;
import com.github.mangila.library.shared.UuidFactory;
import com.github.mangila.library.staging.StagingEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    final List<OpenLibraryAuthor.OpenLibraryLink> openLibraryLinks = author.links();
    final List<String> links = new ArrayList<>();
    if (openLibraryLinks != null) {
      links.addAll(openLibraryLinks.stream().map(OpenLibraryAuthor.OpenLibraryLink::url).toList());
    }
    return new Author(
        id,
        openLibraryKey,
        author.name(),
        author.personal_name(),
        author.alternate_names(),
        author.uris(),
        author.getBioText(),
        author.location(),
        author.birth_date(),
        author.death_date(),
        author.wikipedia(),
        links,
        author.books(),
        author.works());
  }
}
