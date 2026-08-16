package com.github.mangila.library.staging;

import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.author.domain.AuthorFactory;
import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.shared.LibraryType;
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

  public <T> T createNew(StagingEntity stagingEntity, LibraryType libraryType, Class<T> clazz) {
    final Object result =
        switch (libraryType) {
          case AUTHOR -> authorFactory.from(stagingEntity);
          case EDITION -> throw new UnsupportedOperationException("Edition not supported");
          case WORK -> throw new UnsupportedOperationException("Work not supported");
        };
    return clazz.cast(result);
  }

  @SuppressWarnings("unchecked")
  public void saveAll(List<?> batch, LibraryType libraryType) {
    switch (libraryType) {
      case AUTHOR -> authorService.saveAll((List<Author>) batch);
      case EDITION -> throw new UnsupportedOperationException("Edition not supported");
      case WORK -> throw new UnsupportedOperationException("Work not supported");
    }
  }
}
