package com.github.mangila.library.shared;

public enum LibraryType {
  AUTHOR,
  WORK,
  EDITION;

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
