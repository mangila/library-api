package com.github.mangila.library.integration.openlibrary;

import io.smallrye.config.ConfigMapping;
import java.util.List;

@ConfigMapping(prefix = "app.integration.openlibrary")
public interface OpenLibraryConfig {

  boolean downloadEnabled();

  List<String> downloadFileNames();

  boolean etlEnabled();

  List<String> etlFileNames();
}
