package com.github.mangila.library.integration.jobrunr;

import static org.jobrunr.scheduling.JobBuilder.aJob;

import com.github.mangila.library.config.OpenLibraryConfig;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.time.Duration;
import org.jobrunr.scheduling.JobRequestScheduler;

@ApplicationScoped
public class JobRunrScheduler {

  private final OpenLibraryConfig openLibraryConfig;
  private final JobRequestScheduler jobRequestScheduler;

  public JobRunrScheduler(
      OpenLibraryConfig openLibraryConfig, JobRequestScheduler jobRequestScheduler) {
    this.openLibraryConfig = openLibraryConfig;
    this.jobRequestScheduler = jobRequestScheduler;
  }

  public void scheduleAtStartUp(@Observes StartupEvent event) {
    if (openLibraryConfig.downloadEnabled()) {
      openLibraryConfig.downloadFileNames().stream()
          .map(OpenLibraryDownloadJobRequest::new)
          .forEach(
              jobRequest -> {
                final String fileName = jobRequest.fileName();
                final var _ =
                    jobRequestScheduler.create(
                        aJob()
                            .scheduleIn(Duration.ofSeconds(1))
                            .withName("Download: %s".formatted(fileName))
                            .withAmountOfRetries(10)
                            .withLabels("openlibrary", "download")
                            .withJobRequest(jobRequest));
                Log.infof("Scheduled download: %s", fileName);
              });
    }
  }
}
