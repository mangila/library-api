package com.github.mangila.library.config;

import io.smallrye.config.ConfigMapping;
import java.nio.file.Path;

@ConfigMapping(prefix = "app.integration.openlibrary")
public interface OpenLibraryConfig {

  Path dataDirectory();

  boolean downloadEnabled();

  boolean importEnabled();

  boolean processEnabled();
}
