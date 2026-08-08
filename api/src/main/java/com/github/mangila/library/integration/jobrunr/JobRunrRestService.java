package com.github.mangila.library.integration.jobrunr;

import com.github.mangila.library.integration.openlibrary.OpenLibraryConfig;
import com.github.mangila.library.shared.HttpProblemException;
import io.github.mangila.ensure4j.Ensure;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JobRunrRestService {

  private final OpenLibraryConfig openLibraryConfig;
  private final JobRunrScheduler jobRunrScheduler;

  public JobRunrRestService(
      OpenLibraryConfig openLibraryConfig, JobRunrScheduler jobRunrScheduler) {
    this.openLibraryConfig = openLibraryConfig;
    this.jobRunrScheduler = jobRunrScheduler;
  }

  public JobCreatedDto scheduleBackup() {
    return new JobCreatedDto(jobRunrScheduler.databaseBackupJob().asUUID());
  }

  public JobCreatedDto scheduleDownload(String fileName) {
    Ensure.isTrue(
        openLibraryConfig.downloadEnabled(),
        () -> HttpProblemException.badRequest("Download is disabled"));
    final boolean anyMatch =
        openLibraryConfig.downloadFileNames().stream().anyMatch(fileName::equals);
    Ensure.isTrue(anyMatch, () -> HttpProblemException.badRequest("File name not found"));
    return new JobCreatedDto(jobRunrScheduler.openLibraryDownloadJob(fileName).asUUID());
  }
}
