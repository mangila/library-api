package com.github.mangila.library.staging;

import com.github.mangila.library.author.application.Author;
import com.github.mangila.library.author.application.AuthorFactory;
import com.github.mangila.library.author.application.AuthorService;
import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StagingRouter {

  private final AuthorFactory authorFactory;
  private final AuthorService authorService;

  public StagingRouter(AuthorFactory authorFactory, AuthorService authorService) {
    this.authorFactory = authorFactory;
    this.authorService = authorService;
  }

  public Object createNew(StagingEntity stagingEntity, OpenLibraryType openLibraryType) {
    return switch (openLibraryType) {
      case AUTHOR -> authorFactory.from(stagingEntity);
      case EDITION -> throw new UnsupportedOperationException("Edition not supported");
      case WORK -> throw new UnsupportedOperationException("Work not supported");
    };
  }

  @SuppressWarnings("unchecked")
  public int saveAll(List<?> batch, OpenLibraryType openLibraryType) {
    switch (openLibraryType) {
      case AUTHOR -> authorService.saveAll((List<Author>) batch);
      case EDITION -> throw new UnsupportedOperationException("Edition not supported");
      case WORK -> throw new UnsupportedOperationException("Work not supported");
    }
    return batch.size();
  }
}
