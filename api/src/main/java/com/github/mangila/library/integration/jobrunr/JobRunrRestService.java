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

  public JobScheduledDto scheduleBackup() {
    final JobId jobId = jobRunrScheduler.databaseBackupJob();
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleEtl(String fileName) {
    Ensure.isTrue(
        openLibraryConfig.etlEnabled(), () -> HttpProblemException.badRequest("ETL is disabled"));
    final boolean anyMatch = openLibraryConfig.etlFileNames().stream().anyMatch(fileName::equals);
    Ensure.isTrue(
        anyMatch, () -> HttpProblemException.badRequest("File name not configured for ETL"));
    final JobId jobId = jobRunrScheduler.etlJob(fileName);
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleOpenLibraryDownload(String fileName) {
    Ensure.isTrue(
        openLibraryConfig.downloadEnabled(),
        () -> HttpProblemException.badRequest("OpenLibrary download is disabled"));
    final boolean anyMatch =
        openLibraryConfig.downloadFileNames().stream().anyMatch(fileName::equals);
    Ensure.isTrue(
        anyMatch, () -> HttpProblemException.badRequest("File name not configured for download"));
    final JobId jobId = jobRunrScheduler.openLibraryDownloadJob(fileName);
    return JobScheduledDto.from(jobId);
  }
}
