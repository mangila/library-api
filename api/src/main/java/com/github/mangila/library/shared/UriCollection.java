package com.github.mangila.library.shared;

import io.github.mangila.ensure4j.Ensure;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public record UriCollection(List<URI> value) {

  public static final UriCollection EMPTY = new UriCollection(null);

  public UriCollection {
    value = (value == null) ? List.of() : List.copyOf(value);
  }

  public static UriCollection from(List<String> uris) {
    if (uris == null) {
      return EMPTY;
    }
    final List<URI> l = uris.stream().map(UriCollection::parse).toList();
    return new UriCollection(l);
  }

  public List<String> asStringList() {
    return value.stream().map(URI::toString).toList();
  }

  private static URI parse(String uri) {
    Ensure.notBlank(uri, "URI must not be blank");
    try {
      return new URI(uri);
    } catch (URISyntaxException e) {
      throw new RuntimeException("Failed to parse URI: %s".formatted(uri), e);
    }
  }
}
