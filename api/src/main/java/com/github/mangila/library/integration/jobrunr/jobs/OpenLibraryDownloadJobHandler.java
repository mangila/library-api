package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.integration.openlibrary.OpenLibraryDownloadIntegration;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import org.apache.commons.io.FileUtils;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class OpenLibraryDownloadJobHandler
    implements JobRequestHandler<OpenLibraryDownloadJobRequest> {

  private final OpenLibraryDownloadIntegration openLibraryDownloadIntegration;
  private JobDashboardProgressBar jobDashboardProgressBar;

  public OpenLibraryDownloadJobHandler(
      OpenLibraryDownloadIntegration openLibraryDownloadIntegration) {
    this.openLibraryDownloadIntegration = openLibraryDownloadIntegration;
  }

  @Override
  public void run(OpenLibraryDownloadJobRequest jobRequest) {
    final String fileName = jobRequest.fileName();
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final Path dataDir = Path.of("data");
    final Path destination = dataDir.resolve(fileName);
    final Instant startExecution = Instant.now();
    openLibraryDownloadIntegration.downloadToFileSystem(
        destination,
        (transferred, total) -> {
          if (transferred == 0) {
            this.jobDashboardProgressBar = jobContext.progressBar(total);
            jobContext
                .logger()
                .info("File size: %s".formatted(FileUtils.byteCountToDisplaySize(total)));
          } else {
            jobDashboardProgressBar.setProgress(transferred);
            final Instant progressExecution = Instant.now();
            final Duration duration = Duration.between(startExecution, progressExecution);
            final long elapsedSeconds = duration.toSeconds();
            final long bytesPerSecond = transferred / elapsedSeconds;
            final String etaString = getEta(transferred, total, bytesPerSecond);
            jobContext
                .logger()
                .info(
                    "Downloaded: %s / %s (%s) - ETA: %s"
                        .formatted(
                            FileUtils.byteCountToDisplaySize(transferred),
                            FileUtils.byteCountToDisplaySize(total),
                            FileUtils.byteCountToDisplaySize(bytesPerSecond) + "/s",
                            etaString));
          }
        });
    jobContext.logger().info("File download complete: %s".formatted(destination.toAbsolutePath()));
  }

  private String getEta(long transferred, long total, long bytesPerSecond) {
    final long remainingBytes = total - transferred;
    final long remainingSeconds = remainingBytes / bytesPerSecond;
    final Duration eta = Duration.ofSeconds(remainingSeconds);
    final long hours = eta.toHours();
    final long minutes = eta.toMinutesPart();
    final long seconds = eta.toSecondsPart();
    if (hours > 0) {
      return String.format("%dh %02dm %02ds", hours, minutes, seconds);
    } else if (minutes > 0) {
      return String.format("%dm %02ds", minutes, seconds);
    } else {
      return String.format("%ds", seconds);
    }
  }
}
