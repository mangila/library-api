package com.github.mangila.library.shared;

import io.github.mangila.ensure4j.Ensure;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record FilePath(Path value) {

  public File toFile() {
    return value.toFile();
  }

  public String fileName() {
    final Path fileName = Ensure.notNull(value.getFileName());
    return fileName.toString();
  }

  public boolean exists() {
    return Files.exists(value);
  }

  public long size() {
    try {
      return Files.size(value);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public Path toTempFile() {
    return value.resolveSibling(fileName() + ".tmp");
  }
}
