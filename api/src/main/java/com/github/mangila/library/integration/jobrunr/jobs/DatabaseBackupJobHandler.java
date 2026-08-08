package com.github.mangila.library.integration.jobrunr.jobs;

import io.agroal.api.AgroalDataSource;
import io.github.mangila.ensure4j.Ensure;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

/**
 * This is because of SqlLite sandbox, this is needed to get writes on the filesystem <a
 * href="https://quarkus.io/blog/sqlite4j-pure-java-sqlite/">Quarkus SQLite Blog post</a>
 */
@ApplicationScoped
public class DatabaseBackupJobHandler implements JobRequestHandler<DatabaseBackupJobRequest> {

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

  private final String jdbcUrl;
  private final AgroalDataSource dataSource;

  public DatabaseBackupJobHandler(
      @ConfigProperty(name = "quarkus.datasource.jdbc.url") String jdbcUrl,
      AgroalDataSource dataSource) {
    this.jdbcUrl = jdbcUrl;
    this.dataSource = dataSource;
  }

  @SuppressWarnings("java:S2077")
  @Override
  public void run(DatabaseBackupJobRequest jobRequest) throws Exception {
    final JobContext ctx = ThreadLocalJobContext.getJobContext();
    final Path dbFile = resolveDatabasePath(this.jdbcUrl);
    Ensure.notNull(dbFile);
    final Path backupFile = dbFile.resolveSibling("backup.db");
    final long fileSize = Files.size(dbFile);
    ctx.logger()
        .info(
            "Database file: %s - size: %s"
                .formatted(dbFile, FileUtils.byteCountToDisplaySize(fileSize)));
    ctx.logger().info("Database backup file: %s".formatted(backupFile));
    try (var conn = dataSource.getConnection();
        var stmt = conn.createStatement()) {
      // language=sqlite
      final String sql = "BACKUP TO '%s'".formatted(backupFile.toString());
      stmt.executeUpdate(sql);
      Files.move(
          backupFile, dbFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      Files.deleteIfExists(backupFile);
      throw new UncheckedIOException("Failed to move backup file", e);
    }
  }
}
