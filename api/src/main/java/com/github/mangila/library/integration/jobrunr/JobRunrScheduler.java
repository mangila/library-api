package com.github.mangila.library.integration.jobrunr;

import static org.jobrunr.scheduling.JobBuilder.aJob;
import static org.jobrunr.scheduling.RecurringJobBuilder.*;

import com.github.mangila.library.integration.jobrunr.jobs.FileDownloadJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.FileImportJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.StagingDeleteJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.StagingProcessJobRequest;
import com.github.mangila.library.shared.FilePath;
import com.github.mangila.library.shared.LibraryType;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.nio.file.Path;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobRequestScheduler;
import org.jobrunr.scheduling.cron.Cron;

@ApplicationScoped
public class JobRunrScheduler {

  private static final Path DATA_DIR = Path.of("data");

  private final JobRequestScheduler jobRequestScheduler;

  public JobRunrScheduler(JobRequestScheduler jobRequestScheduler) {
    this.jobRequestScheduler = jobRequestScheduler;
  }

  public void onApplicationStart(@Observes StartupEvent event) {
    jobRequestScheduler.createRecurrently(
        aRecurringJob()
            .withName("Staging delete")
            .withCron(Cron.every5minutes())
            .withLabels("openlibrary", "delete")
            .withJobRequest(new StagingDeleteJobRequest()));
    Log.infof("Scheduled staging delete");
  }

  public JobId scheduleFileDownload(LibraryType libraryType) {
    final FilePath filePath = getFilePath(libraryType);
    Log.infof("Scheduling file download: %s", filePath);
    return jobRequestScheduler.create(
        aJob()
            .withName("Download: %s".formatted(libraryType.getFileName()))
            .withAmountOfRetries(10)
            .withLabels("openlibrary", "download")
            .withJobRequest(new FileDownloadJobRequest(filePath)));
  }

  public JobId scheduleFileImport(LibraryType libraryType) {
    final FilePath filePath = getFilePath(libraryType);
    Log.infof("Scheduling file import: %s", filePath);
    return jobRequestScheduler.create(
        aJob()
            .withName("Import: %s".formatted(libraryType.getFileName()))
            .withAmountOfRetries(3)
            .withLabels("openlibrary", "import")
            .withJobRequest(new FileImportJobRequest(filePath)));
  }

  public JobId scheduleStagingProcessing(LibraryType libraryType, int limit) {
    Log.infof("Scheduling file processing: %s", libraryType);
    return jobRequestScheduler.create(
        aJob()
            .withName("Staging Process: %s".formatted(libraryType))
            .withAmountOfRetries(10)
            .withLabels("openlibrary", "process")
            .withJobRequest(new StagingProcessJobRequest(libraryType, limit)));
  }

  private FilePath getFilePath(LibraryType libraryType) {
    final String fileName = libraryType.getFileName();
    final Path path = DATA_DIR.resolve(fileName);
    return new FilePath(path);
  }
}
