package com.github.mangila.library.integration.openlibrary;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@ConfigMapping(prefix = "app.integration.openlibrary")
public interface OpenLibraryConfig {

  boolean downloadEnabled();

  @NotEmpty List<String> downloadFileNames();

  boolean importEnabled();

  @NotEmpty List<String> importFileNames();
}
