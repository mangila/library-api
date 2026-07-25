package com.github.mangila.library.integration.jobrunr.rest;

import com.github.mangila.library.config.OpenLibraryConfig;
import com.github.mangila.library.integration.jobrunr.application.JobRunrScheduler;
import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
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

  public JobScheduledDto scheduleFileDownload(OpenLibraryType openLibraryType) {
    Ensure.isTrue(
        openLibraryConfig.downloadEnabled(),
        () -> HttpProblemException.badRequest("File download is disabled"));
    final JobId jobId = jobRunrScheduler.scheduleFileDownload(openLibraryType);
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleFileImport(OpenLibraryType openLibraryType) {
    Ensure.isTrue(
        openLibraryConfig.importEnabled(),
        () -> HttpProblemException.badRequest("Import is disabled"));
    final JobId jobId = jobRunrScheduler.scheduleFileImport(openLibraryType);
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleStagingProcessing(OpenLibraryType openLibraryType, int limit) {
    Ensure.isTrue(
        openLibraryConfig.processEnabled(),
        () -> HttpProblemException.badRequest("Processing is disabled"));
    final JobId jobId = jobRunrScheduler.scheduleStagingProcessing(openLibraryType, limit);
    return JobScheduledDto.from(jobId);
  }
}
