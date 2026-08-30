package com.github.mangila.library.staging;

import com.github.mangila.library.shared.LibraryType;
import io.github.mangila.ensure4j.Ensure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
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

  @Transactional(Transactional.TxType.REQUIRED)
  public long deleteAllProcessed() {
    return stagingRepository.delete("processed = true");
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public Stream<StagingEntity> streamByType(LibraryType libraryType) {
    return stagingRepository
        .find("processed = false and type = ?1", libraryType.getType())
        .stream();
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public void updateProcessed(List<StagingEntity> entities) {
    Ensure.notEmpty(entities);
    List<String> ids = entities.stream().map(StagingEntity::getKey).toList();
    stagingRepository.update("processed = true WHERE id IN (?1)", ids);
  }
}
