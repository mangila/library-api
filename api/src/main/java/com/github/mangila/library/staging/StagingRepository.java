package com.github.mangila.library.staging;

import com.github.mangila.library.shared.ProgressInputStream;
import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import org.apache.commons.io.FileUtils;
import org.intellij.lang.annotations.Language;
import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;

@ApplicationScoped
public class StagingRepository implements PanacheRepositoryBase<StagingEntity, String> {

  private final AgroalDataSource agroalDataSource;

  public StagingRepository(AgroalDataSource agroalDataSource) {
    this.agroalDataSource = agroalDataSource;
  }

  public long copyToPostgres(
      InputStream stream, long contentLength, Consumer<Long> progressCallback) {
    try (Connection connection = agroalDataSource.getConnection();
        ProgressInputStream progressInputStream =
            new ProgressInputStream(stream, contentLength, progressCallback)) {
      PGConnection pgConnection = connection.unwrap(PGConnection.class);
      CopyManager copyManager = pgConnection.getCopyAPI();
      @Language("postgresql")
      final String sql =
          """
          COPY staging (type, key, revision, last_modified, json) FROM STDIN
          WITH (FORMAT text, DELIMITER '\t', HEADER true, ON_ERROR ignore)
          """;
      return copyManager.copyIn(sql, progressInputStream, (int) FileUtils.ONE_KB * 64);
    } catch (IOException | SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
