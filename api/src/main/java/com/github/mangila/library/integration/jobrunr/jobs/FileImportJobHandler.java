package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.shared.FilePath;
import com.github.mangila.library.staging.StagingDataService;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;
import org.apache.commons.io.FileUtils;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class FileImportJobHandler implements JobRequestHandler<FileImportJobRequest> {

  private final StagingDataService stagingDataService;

  public FileImportJobHandler(StagingDataService stagingDataService) {
    this.stagingDataService = stagingDataService;
  }

  @Override
  public void run(FileImportJobRequest jobRequest) throws Exception {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final FilePath filePath = jobRequest.filePath();
    if (!filePath.exists()) {
      jobContext.logger().warn("File does not exist: %s".formatted(filePath.value()));
      return;
    }
    final long estimatedDecompressedSize = filePath.size() * 8;
    final JobDashboardProgressBar jobDashboardProgressBar =
        jobContext.progressBar(estimatedDecompressedSize);
    jobContext
        .logger()
        .info(
            """
            File: %s
            Gzipped size: %s
            Decompressed estimated size: %s
            """
                .formatted(
                    filePath.value(),
                    FileUtils.byteCountToDisplaySize(filePath.size()),
                    FileUtils.byteCountToDisplaySize(estimatedDecompressedSize)));
    final Instant startExecution = Instant.now();
    Consumer<Long> progressCallback =
        transferred -> {
          jobDashboardProgressBar.setProgress(transferred);
          final Instant progressExecution = Instant.now();
          final Duration duration = Duration.between(startExecution, progressExecution);
          final long elapsedSeconds = Math.max(1, duration.toSeconds());
          final long bytesPerSecond = transferred / elapsedSeconds;
          jobContext
              .logger()
              .info(
                  "%s / %s (%s) - %s"
                      .formatted(
                          FileUtils.byteCountToDisplaySize(transferred),
                          FileUtils.byteCountToDisplaySize(estimatedDecompressedSize),
                          FileUtils.byteCountToDisplaySize(bytesPerSecond) + "/s",
                          jobDashboardProgressBar.getProgressAsPercentage() + "%"));
        };
    try (InputStream inputStream = Files.newInputStream(filePath.value(), StandardOpenOption.READ);
        GZIPInputStream gzip = new GZIPInputStream(inputStream, (int) (FileUtils.ONE_KB * 32))) {
      long rows =
          stagingDataService.copyToPostgres(gzip, estimatedDecompressedSize, progressCallback);
      jobContext.logger().info("Imported: %s rows".formatted(rows));
      jobDashboardProgressBar.setProgress(estimatedDecompressedSize);
    }
  }
}
