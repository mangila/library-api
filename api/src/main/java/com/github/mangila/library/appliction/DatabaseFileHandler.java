package com.github.mangila.library.appliction;

import io.github.mangila.ensure4j.Ensure;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Startup
@ApplicationScoped
public class DatabaseFileHandler {

  private static final String SQLITE_JDBC_PREFIX = "jdbc:sqlite:";

  private Path dbFile;
  private Path backupFile;
  private final String jdbcUrl;

  public DatabaseFileHandler(@ConfigProperty(name = "quarkus.datasource.jdbc.url") String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public Path getBackupFile() {
    return backupFile;
  }

  public Path getDbFile() {
    return dbFile;
  }

  public long getFileSize() throws IOException {
    return Files.size(dbFile);
  }

  @PostConstruct
  public void init() throws IOException {
    this.dbFile = resolveDatabasePath(jdbcUrl);
    final Path fileName = Ensure.notNull(dbFile.getFileName());
    this.backupFile = dbFile.resolveSibling(fileName + ".bak");
    Log.infof(
        "%sDatabase file: %s%sSize: %s",
        System.lineSeparator(),
        dbFile,
        System.lineSeparator(),
        FileUtils.byteCountToDisplaySize(getFileSize()));
  }

  private Path resolveDatabasePath(String jdbcUrl) {
    if (jdbcUrl == null || !jdbcUrl.startsWith(SQLITE_JDBC_PREFIX)) {
      throw new IllegalStateException(
          "Expected SQLite JDBC URL to start with %s, but got %s"
              .formatted(SQLITE_JDBC_PREFIX, jdbcUrl));
    }
    final String jdbcFile = jdbcUrl.substring(SQLITE_JDBC_PREFIX.length());
    if (jdbcFile.isBlank()) {
      throw new IllegalStateException(
          "SQLite database path is empty in JDBC URL: %s".formatted(jdbcUrl));
    }
    return Paths.get(jdbcFile).toAbsolutePath().normalize();
  }
}
