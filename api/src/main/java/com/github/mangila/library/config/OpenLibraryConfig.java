package com.github.mangila.library.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "app.integration.openlibrary")
public interface OpenLibraryConfig {

  boolean downloadEnabled();

  boolean importEnabled();

  boolean processEnabled();
}
