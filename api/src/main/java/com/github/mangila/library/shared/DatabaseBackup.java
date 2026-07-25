package com.github.mangila.library.shared;

import static io.quarkus.scheduler.Scheduled.ConcurrentExecution.SKIP;

import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * <a href="https://quarkus.io/blog/sqlite4j-pure-java-sqlite/">Reason why we create a backup</a>
 */
@ApplicationScoped
public class DatabaseBackup {

  private static final String SQLITE_JDBC_PREFIX = "jdbc:sqlite:";

  private static Path resolveDatabasePath(String jdbcUrl) {
    if (jdbcUrl == null || !jdbcUrl.startsWith(SQLITE_JDBC_PREFIX)) {
      throw new IllegalStateException(
          "Expected SQLite JDBC URL to start with " + SQLITE_JDBC_PREFIX + ", but got: " + jdbcUrl);
    }

    String dbFile = jdbcUrl.substring(SQLITE_JDBC_PREFIX.length());

    if (dbFile.isBlank()) {
      throw new IllegalStateException("SQLite database path is empty in JDBC URL: " + jdbcUrl);
    }

    return Paths.get(dbFile).toAbsolutePath().normalize();
  }

  private final Path backupDbFilePath;
  private final AgroalDataSource dataSource;
  private final Path originalDbFilePath;
  private final String sql;

  public DatabaseBackup(
      @ConfigProperty(name = "quarkus.datasource.jdbc.url") String jdbcUrl,
      AgroalDataSource dataSource) {
    this.dataSource = dataSource;
    this.originalDbFilePath = resolveDatabasePath(jdbcUrl);
    this.backupDbFilePath =
        originalDbFilePath
            .getParent()
            .resolve("backup_" + originalDbFilePath.getFileName().toString());
    // language=sqlite
    this.sql = "BACKUP TO '%s'".formatted(backupDbFilePath.toString());
  }

  public void onShutdown(@Observes ShutdownEvent event) {
    Log.info("creating database backup before shutdown");
    backup();
  }

  @Scheduled(delayed = "10s", every = "10s", concurrentExecution = SKIP)
  public void scheduled() {
    backup();
  }

  private void backup() {
    try {
      try (var conn = dataSource.getConnection();
          var stmt = conn.createStatement()) {
        stmt.executeUpdate(sql);
        Files.move(
            backupDbFilePath,
            originalDbFilePath,
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (SQLException | IOException e) {
      throw new RuntimeException("Failed to back up the database", e);
    }
  }
}
