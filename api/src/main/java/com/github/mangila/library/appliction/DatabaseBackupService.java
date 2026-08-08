package com.github.mangila.library.appliction;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@ApplicationScoped
public class DatabaseBackupService {

  private final DatabaseFileHandler databaseFileHandler;
  private final AgroalDataSource dataSource;

  public DatabaseBackupService(
      DatabaseFileHandler databaseFileHandler, AgroalDataSource dataSource) {
    this.databaseFileHandler = databaseFileHandler;
    this.dataSource = dataSource;
  }

  public Path dbFile() {
    return databaseFileHandler.getDbFile();
  }

  /**
   * This is because of SqlLite sandbox, this is needed to get writes on the filesystem <a
   * href="https://quarkus.io/blog/sqlite4j-pure-java-sqlite/">Quarkus SQLite Blog post</a>
   */
  @SuppressWarnings("java:S2077")
  public void execute() {
    final Path dbFile = databaseFileHandler.getDbFile();
    final Path backupFile = databaseFileHandler.getBackupFile();
    try (final Connection connection = dataSource.getConnection();
        final Statement statement = connection.createStatement()) {
      // language=sqlite
      final String sql = "BACKUP TO '%s'".formatted(backupFile.toString());
      statement.executeUpdate(sql);
      Files.move(
          backupFile, dbFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      try {
        Files.deleteIfExists(backupFile);
      } catch (IOException _) {
        // do nothing
      }
      throw new UncheckedIOException("Failed to move backup file", e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public long size() {
    try {
      return databaseFileHandler.getFileSize();
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to get database file size", e);
    }
  }
}
