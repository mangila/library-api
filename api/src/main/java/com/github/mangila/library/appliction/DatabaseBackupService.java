package com.github.mangila.library.appliction;

import io.agroal.api.AgroalDataSource;
import io.github.mangila.ensure4j.Ensure;
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

  /**
   * This is because of SqlLite sandbox, this is needed to get writes on the filesystem <a
   * href="https://quarkus.io/blog/sqlite4j-pure-java-sqlite/">Quarkus SQLite Blog post</a>
   */
  @SuppressWarnings("java:S2077")
  public void createBackup() {
    final Path dbFile = databaseFileHandler.getDbFile();
    final Path fileName = Ensure.notNull(dbFile.getFileName());
    final Path backupFile = dbFile.resolveSibling(fileName + ".bak");
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

  public Path dbFile() {
    return databaseFileHandler.getDbFile();
  }

  public long size() {
    return databaseFileHandler.getFileSize();
  }
}
