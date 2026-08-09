package com.github.mangila.library.integration.jobrunr;

import com.github.mangila.library.integration.openlibrary.OpenLibraryConfig;
import com.github.mangila.library.shared.HttpProblemException;
import io.github.mangila.ensure4j.Ensure;
import jakarta.enterprise.context.ApplicationScoped;
import org.jobrunr.jobs.JobId;

@ApplicationScoped
public class JobRunrRestService {

  private final OpenLibraryConfig openLibraryConfig;
  private final JobRunrScheduler jobRunrScheduler;

  public JobRunrRestService(
      OpenLibraryConfig openLibraryConfig, JobRunrScheduler jobRunrScheduler) {
    this.openLibraryConfig = openLibraryConfig;
    this.jobRunrScheduler = jobRunrScheduler;
  }

  public JobScheduledDto scheduleFileDownload(String fileName) {
    Ensure.isTrue(
        openLibraryConfig.downloadEnabled(),
        () -> HttpProblemException.badRequest("File download is disabled"));
    final boolean anyMatch =
        openLibraryConfig.downloadFileNames().stream().anyMatch(fileName::equals);
    Ensure.isTrue(
        anyMatch, () -> HttpProblemException.badRequest("File name not configured for download"));
    final JobId jobId = jobRunrScheduler.scheduleFileDownload(fileName);
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleFileImport(String fileName) {
    Ensure.isTrue(
        openLibraryConfig.importEnabled(),
        () -> HttpProblemException.badRequest("Import is disabled"));
    final boolean anyMatch =
        openLibraryConfig.importFileNames().stream().anyMatch(fileName::equals);
    Ensure.isTrue(
        anyMatch, () -> HttpProblemException.badRequest("File name not configured for import"));
    final JobId jobId = jobRunrScheduler.scheduleFileImport(fileName);
    return JobScheduledDto.from(jobId);
  }
}
