package com.github.mangila.library.staging;

import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
@Valid public class StagingService {

  private final StagingDataService stagingDataService;
  private final StagingRouter stagingRouter;

  public StagingService(StagingDataService stagingDataService, StagingRouter stagingRouter) {
    this.stagingDataService = stagingDataService;
    this.stagingRouter = stagingRouter;
  }

  public long copyToPostgres(
      @NotNull InputStream stream,
      @Positive long contentLength,
      @NotNull Consumer<Long> progressCallback) {
    return stagingDataService.copyToPostgres(stream, contentLength, progressCallback);
  }

  public Object createNew(StagingEntity stagingEntity, OpenLibraryType openLibraryType) {
    return stagingRouter.createNew(stagingEntity, openLibraryType);
  }

  public long deleteAllByProcessed(boolean processed) {
    return stagingDataService.deleteAllByProcessed(processed);
  }

  public int saveAll(List<?> batch, OpenLibraryType openLibraryType) {
    return stagingRouter.saveAll(batch, openLibraryType);
  }

  public Stream<StagingEntity> streamProcessedAndType(
      boolean processed, OpenLibraryType openLibraryType) {
    return stagingDataService.streamProcessedAndType(processed, openLibraryType);
  }

  public void updateProcessedWhereIdIn(boolean processed, List<String> ids) {
    stagingDataService.updateProcessedWhereIdIn(processed, ids);
  }
}
