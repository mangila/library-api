package com.github.mangila.library.integration.jobrunr;

import static org.jobrunr.scheduling.JobBuilder.aJob;

import com.github.mangila.library.integration.jobrunr.jobs.DatabaseBackupJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.OpenLibraryDownloadJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.OpenLibraryEtlJobRequest;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobRequestScheduler;
import org.jobrunr.scheduling.RecurringJobBuilder;
import org.jobrunr.scheduling.cron.Cron;

@ApplicationScoped
public class JobRunrScheduler {

  private final JobRequestScheduler jobRequestScheduler;

  public JobRunrScheduler(JobRequestScheduler jobRequestScheduler) {
    this.jobRequestScheduler = jobRequestScheduler;
  }

  public String databaseBackupRecurringJob() {
    return jobRequestScheduler.createRecurrently(
        RecurringJobBuilder.aRecurringJob()
            .withCron(Cron.every5minutes())
            .withName("Database Backup")
            .withAmountOfRetries(10)
            .withLabels("database", "backup")
            .withJobRequest(new DatabaseBackupJobRequest()));
  }

  public JobId openLibraryDownloadJob(String fileName) {
    return jobRequestScheduler.create(
        aJob()
            .scheduleIn(Duration.ofSeconds(1))
            .withName("Download: %s".formatted(fileName))
            .withAmountOfRetries(10)
            .withLabels("openlibrary", "download")
            .withJobRequest(new OpenLibraryDownloadJobRequest(fileName)));
  }

  public JobId openLibraryEtlJob(String fileName) {
    return jobRequestScheduler.create(
        aJob()
            .scheduleIn(Duration.ofSeconds(1))
            .withName("ETL: %s".formatted(fileName))
            .withAmountOfRetries(10)
            .withLabels("openlibrary", "etl")
            .withJobRequest(new OpenLibraryEtlJobRequest(fileName)));
  }
}
