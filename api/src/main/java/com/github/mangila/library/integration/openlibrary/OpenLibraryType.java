package com.github.mangila.library.integration.openlibrary;

import com.github.mangila.library.author.application.Author;

public enum OpenLibraryType {
  AUTHOR,
  WORK,
  EDITION;

  public Class<?> getClazz() {
    return switch (this) {
      case AUTHOR -> Author.class;
      case EDITION -> throw new UnsupportedOperationException("Edition not supported");
      case WORK -> throw new UnsupportedOperationException("Work not supported");
    };
  }

  public String getFileName() {
    return switch (this) {
      case AUTHOR -> "ol_dump_authors_latest.txt.gz";
      case WORK -> "ol_dump_works_latest.txt.gz";
      case EDITION -> "ol_dump_editions_latest.txt.gz";
    };
  }

  public String getType() {
    return switch (this) {
      case AUTHOR -> "/type/author";
      case WORK -> "/type/work";
      case EDITION -> "/type/edition";
    };
  }
}
