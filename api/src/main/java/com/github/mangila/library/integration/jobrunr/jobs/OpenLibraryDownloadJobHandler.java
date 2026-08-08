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
    final JobContext ctx = ThreadLocalJobContext.getJobContext();
    final Path dataDir = Path.of("data");
    final Path destination = dataDir.resolve(fileName);
    final Instant startExecution = Instant.now();
    final Path path =
        openLibraryDownloadIntegration.downloadToFileSystem(
            destination,
            (transferred, total) -> {
              if (transferred == 0) {
                this.jobDashboardProgressBar = ctx.progressBar(total);
                ctx.logger()
                    .info("File size: %s".formatted(FileUtils.byteCountToDisplaySize(total)));
              } else {
                jobDashboardProgressBar.setProgress(transferred);
                Instant progressExecution = Instant.now();
                long elapsedSeconds =
                    Duration.between(startExecution, progressExecution).toSeconds();
                long bytesPerSecond = transferred / elapsedSeconds;
                final String etaString = getEta(transferred, total, bytesPerSecond);
                ctx.logger()
                    .info(
                        "Downloaded: %s / %s (%s) - ETA: %s"
                            .formatted(
                                FileUtils.byteCountToDisplaySize(transferred),
                                FileUtils.byteCountToDisplaySize(total),
                                FileUtils.byteCountToDisplaySize(bytesPerSecond) + "/s",
                                etaString));
              }
            });
    ctx.logger().info("File downloaded to %s".formatted(path.toAbsolutePath()));
  }

  private String getEta(long transferred, long total, long bytesPerSecond) {
    long remainingBytes = total - transferred;
    long remainingSeconds = remainingBytes / bytesPerSecond;
    final Duration eta = Duration.ofSeconds(remainingSeconds);
    long hours = eta.toHours();
    long minutes = eta.toMinutesPart();
    long seconds = eta.toSecondsPart();
    if (hours > 0) {
      return String.format("%dh %02dm %02ds", hours, minutes, seconds);
    } else if (minutes > 0) {
      return String.format("%dm %02ds", minutes, seconds);
    } else {
      return String.format("%ds", seconds);
    }
  }
}
