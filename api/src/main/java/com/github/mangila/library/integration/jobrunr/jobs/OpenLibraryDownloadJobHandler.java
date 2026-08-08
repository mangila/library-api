package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.integration.openlibrary.OpenLibraryDownloadIntegration;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.file.Path;
import java.time.Duration;
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
    final long startTime = System.nanoTime();
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
                long elapsedSeconds = Duration.ofNanos(System.nanoTime() - startTime).toSeconds();
                long bytesPerSecond = transferred / elapsedSeconds;
                ctx.logger()
                    .info(
                        "Downloaded: %s / %s (%s)"
                            .formatted(
                                FileUtils.byteCountToDisplaySize(transferred),
                                FileUtils.byteCountToDisplaySize(total),
                                FileUtils.byteCountToDisplaySize(bytesPerSecond) + "/s"));
              }
            });
    ctx.logger().info("File downloaded to %s".formatted(path.toAbsolutePath()));
  }
}
