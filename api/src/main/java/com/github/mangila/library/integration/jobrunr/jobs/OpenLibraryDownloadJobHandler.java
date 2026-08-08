package com.github.mangila.library.integration.jobrunr.jobs;

import static org.apache.commons.io.FileUtils.ONE_MB;

import com.github.mangila.library.integration.openlibrary.OpenLibraryDownloadIntegration;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.file.Path;
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
    Path dataDir = Path.of("data");
    Path destination = dataDir.resolve(fileName);
    final Path path =
        openLibraryDownloadIntegration.downloadToFileSystem(
            destination,
            (transferred, total) -> {
              if (transferred == 0) {
                this.jobDashboardProgressBar = ctx.progressBar(total);
                ctx.logger()
                    .info("File size: %s: %.2f MB".formatted(fileName, (double) total / ONE_MB));
              }
              double megabytes = (double) transferred / (ONE_MB);
              jobDashboardProgressBar.setProgress(transferred);
              ctx.logger().info("%.2f MB downloaded".formatted(megabytes));
            });
    ctx.logger().info("File downloaded to %s".formatted(path));
  }
}
