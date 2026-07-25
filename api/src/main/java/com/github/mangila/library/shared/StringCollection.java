package com.github.mangila.library.shared;

import java.util.List;

public record StringCollection(List<String> value) {

  public static final StringCollection EMPTY = new StringCollection(null);

  public StringCollection {
    value = (value == null) ? List.of() : List.copyOf(value);
  }
}
