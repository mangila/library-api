package com.github.mangila.library.integration.openlibrary;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.integration.openlibrary")
public interface OpenLibraryConfig {

  boolean downloadEnabled();

  boolean importEnabled();

  boolean processEnabled();
}
