package com.github.mangila.library.staging;

import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@ApplicationScoped
public class StagingDataService {

  private final StagingRepository stagingRepository;

  public StagingDataService(StagingRepository stagingRepository) {
    this.stagingRepository = stagingRepository;
  }

  public long copyToPostgres(
      InputStream stream, long contentLength, Consumer<Long> progressCallback) {
    return stagingRepository.copyToPostgres(stream, contentLength, progressCallback);
  }

  public long deleteAllByProcessed(boolean processed) {
    return stagingRepository.delete("processed = ?1", processed);
  }

  public Stream<StagingEntity> streamProcessedAndType(
      boolean processed, OpenLibraryType openLibraryType) {
    return stagingRepository
        .find("processed = ?1 and type = ?2", processed, openLibraryType.getType())
        .stream();
  }

  public void updateProcessedWhereIdIn(boolean processed, List<String> ids) {
    stagingRepository.update("processed = ?1 WHERE id IN (?2)", processed, ids);
  }
}
