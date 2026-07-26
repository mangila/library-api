package com.github.mangila.library.config;

import io.smallrye.config.ConfigMapping;
import java.util.List;

@ConfigMapping(prefix = "app.integration.openlibrary")
public interface OpenLibraryConfig {

  boolean downloadEnabled();

  List<String> downloadFileNames();
}
