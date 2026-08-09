package com.github.mangila.library.integration.jobrunr;

import static org.jobrunr.scheduling.JobBuilder.aJob;

import com.github.mangila.library.integration.jobrunr.jobs.FileDownloadJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.author.AuthorImportJobRequest;
import com.github.mangila.library.integration.jobrunr.jobs.author.AuthorProcessJobRequest;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.apache.commons.csv.CSVRecord;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobRequestScheduler;

@ApplicationScoped
public class JobRunrScheduler {

  private final JobRequestScheduler jobRequestScheduler;

  public JobRunrScheduler(JobRequestScheduler jobRequestScheduler) {
    this.jobRequestScheduler = jobRequestScheduler;
  }

  public void enqueueAuthorProcess(List<CSVRecord> csvRecords) {
    csvRecords.stream()
        .map(CSVRecord::toMap)
        .map(
            csvRecord ->
                aJob()
                    .withName("Process CSV: %s".formatted(csvRecord.get("key")))
                    .withAmountOfRetries(10)
                    .withLabels("openlibrary", "process", "author")
                    .withJobRequest(
                        new AuthorProcessJobRequest((LinkedHashMap<String, String>) csvRecord)))
        .forEach(jobRequestScheduler::create);
  }

  public JobId scheduleFileDownload(String fileName) {
    return jobRequestScheduler.create(
        aJob()
            .scheduleIn(Duration.ofSeconds(1))
            .withName("Download: %s".formatted(fileName))
            .withAmountOfRetries(3)
            .withLabels("openlibrary", "download")
            .withJobRequest(new FileDownloadJobRequest(fileName)));
  }

  public JobId scheduleFileImport(String fileName) {
    return switch (fileName) {
      case "ol_dump_authors_latest.txt.gz" -> scheduleAuthorImport(fileName);
      case "ol_dump_works_latest.txt.gz" -> scheduleWorksImport(fileName);
      case "ol_dump_editions_latest.txt.gz" -> scheduleEditionsImport(fileName);
      default -> throw new IllegalArgumentException("File name not configured for import");
    };
  }

  private JobId scheduleAuthorImport(String fileName) {
    return jobRequestScheduler.create(
        aJob()
            .scheduleIn(Duration.ofSeconds(1))
            .withName("Import: %s".formatted(fileName))
            .withAmountOfRetries(10)
            .withLabels("openlibrary", "import", "author")
            .withJobRequest(new AuthorImportJobRequest(fileName)));
  }

  private JobId scheduleEditionsImport(String fileName) {
    return new JobId(new UUID(0, 0));
  }

  private JobId scheduleWorksImport(String fileName) {
    return new JobId(new UUID(0, 0));
  }
}
