package com.github.mangila.library.integration.jobrunr;

import com.github.mangila.library.integration.openlibrary.OpenLibraryConfig;
import com.github.mangila.library.shared.HttpProblemException;
import com.github.mangila.library.shared.LibraryType;
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

  public JobScheduledDto scheduleFileDownload(LibraryType libraryType) {
    Ensure.isTrue(
        openLibraryConfig.downloadEnabled(),
        () -> HttpProblemException.badRequest("File download is disabled"));
    final JobId jobId = jobRunrScheduler.scheduleFileDownload(libraryType);
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleFileImport(LibraryType libraryType) {
    Ensure.isTrue(
        openLibraryConfig.importEnabled(),
        () -> HttpProblemException.badRequest("Import is disabled"));
    final JobId jobId = jobRunrScheduler.scheduleFileImport(libraryType);
    return JobScheduledDto.from(jobId);
  }

  public JobScheduledDto scheduleStagingProcessing(LibraryType libraryType, int limit) {
    Ensure.isTrue(
        openLibraryConfig.processEnabled(),
        () -> HttpProblemException.badRequest("Processing is disabled"));
    final JobId jobId = jobRunrScheduler.scheduleStagingProcessing(libraryType, limit);
    return JobScheduledDto.from(jobId);
  }
}
