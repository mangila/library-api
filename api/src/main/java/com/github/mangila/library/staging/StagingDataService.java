package com.github.mangila.library.staging;

import com.github.mangila.library.shared.LibraryType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.function.Consumer;

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

  @Transactional
  public long deleteAllProcessed() {
    return stagingRepository.delete("processed = true");
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public List<StagingEntity> findBy(LibraryType libraryType, int limit) {
    return stagingRepository
        .find("processed = false and type = ?1", libraryType.getType())
        .withLock(LockModeType.PESSIMISTIC_WRITE)
        .withHint("jakarta.persistence.lock.timeout", "-2")
        .page(0, limit)
        .list();
  }
}
