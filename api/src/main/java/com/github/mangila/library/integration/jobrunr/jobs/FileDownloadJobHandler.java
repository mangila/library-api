package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.integration.openlibrary.OpenLibraryClient;
import com.github.mangila.library.shared.FilePath;
import com.github.mangila.library.shared.ProgressInputStream;
import io.github.mangila.ensure4j.Ensure;
import io.netty.handler.codec.http.HttpHeaderNames;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class FileDownloadJobHandler implements JobRequestHandler<FileDownloadJobRequest> {

  private final OpenLibraryClient openLibraryClient;

  public FileDownloadJobHandler(@RestClient OpenLibraryClient openLibraryClient) {
    this.openLibraryClient = openLibraryClient;
  }

  @Override
  public void run(FileDownloadJobRequest jobRequest) throws IOException {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final FilePath filePath = jobRequest.filePath();
    final long contentLength =
        jobContext.runStepOnce(
            "Get file metadata: %s".formatted(filePath.fileName()),
            () -> {
              try (Response response = openLibraryClient.metadata(filePath.fileName())) {
                Ensure.isTrue(
                    response.getStatus() == 200,
                    "response must be 200 but was %s".formatted(response.getStatus()));
                final MultivaluedMap<String, String> headers = response.getStringHeaders();
                final String acceptRanges =
                    headers.getFirst(HttpHeaderNames.ACCEPT_RANGES.toString());
                Ensure.equalTo(
                    acceptRanges,
                    "bytes",
                    HttpHeaderNames.ACCEPT_RANGES + " header must equal to 'bytes'");
                headers.forEach(
                    (key, value) -> jobContext.logger().info("%s: %s".formatted(key, value)));
                final long length = parseContentLength(headers);
                try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "rw");
                    RandomAccessFile rafTemp =
                        new RandomAccessFile(filePath.toTempPath().toFile(), "rw")) {
                  raf.setLength(length);
                  rafTemp.setLength(length);
                }
                return length;
              }
            });
    final JobDashboardProgressBar jobDashboardProgressBar = jobContext.progressBar(contentLength);
    final Instant startExecution = Instant.now();
    Consumer<Long> progressCallback =
        transferred -> {
          jobDashboardProgressBar.setProgress(transferred);
          final long currentRange = getCurrentRange(jobContext);
          final long newRange = currentRange + transferred;
          setCurrentRange(jobContext, newRange);
          final Instant progressExecution = Instant.now();
          final Duration duration = Duration.between(startExecution, progressExecution);
          final long elapsedSeconds = Math.max(1, duration.toSeconds());
          final long bytesPerSecond = transferred / elapsedSeconds;
          final String etaString = getEta(transferred, contentLength, bytesPerSecond);
          jobContext
              .logger()
              .info(
                  """
                  %s / %s (%s) - %s
                  %s
                  %s
                  """
                      .formatted(
                          FileUtils.byteCountToDisplaySize(transferred),
                          FileUtils.byteCountToDisplaySize(contentLength),
                          FileUtils.byteCountToDisplaySize(bytesPerSecond) + "/s",
                          jobDashboardProgressBar.getProgressAsPercentage() + "%",
                          getRangeHeaderValue(newRange, contentLength - 1),
                          etaString));
        };
    final long currentRange = getCurrentRange(jobContext);
    final String rangeHeaderValue = getRangeHeaderValue(currentRange, contentLength - 1);
    jobContext
        .logger()
        .info(
            """
            Content length: %s
            Range header: %s
            """
                .formatted(FileUtils.byteCountToDisplaySize(contentLength), rangeHeaderValue));
    final Path tempPath = filePath.toTempPath();
    try (final RestResponse<InputStream> response =
        openLibraryClient.download(filePath.fileName(), rangeHeaderValue)) {
      Ensure.isTrue(response.hasEntity());
      try (BufferedInputStream inputStream =
              new BufferedInputStream(
                  new ProgressInputStream(response.getEntity(), contentLength, progressCallback),
                  (int) FileUtils.ONE_KB * 32);
          FileChannel fileChannel = FileChannel.open(tempPath, StandardOpenOption.WRITE)) {
        fileChannel.position(currentRange);
        try (OutputStream outputStream = Channels.newOutputStream(fileChannel)) {
          inputStream.transferTo(outputStream);
        }
        Files.move(
            tempPath,
            filePath.value(),
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING);
      }
    }
    Files.deleteIfExists(tempPath);
    jobContext.logger().info("File download complete: %s".formatted(filePath.value()));
  }

  private long getCurrentRange(JobContext jobContext) {
    return Objects.requireNonNullElse(jobContext.getMetadata("currentRange"), 0L);
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

  private String getRangeHeaderValue(long currentRange, long contentLength) {
    return "bytes=%s-%s".formatted(currentRange, contentLength);
  }

  private long parseContentLength(MultivaluedMap<String, String> headers) {
    final String length = headers.getFirst(HttpHeaderNames.CONTENT_LENGTH.toString());
    Ensure.notBlank(length, HttpHeaderNames.CONTENT_LENGTH + " must be present");
    final long contentLength = Long.parseLong(length);
    Ensure.positive(contentLength, HttpHeaderNames.CONTENT_LENGTH + " value must be positive");
    return contentLength;
  }

  private void setCurrentRange(JobContext jobContext, long currentRange) {
    jobContext.saveMetadata("currentRange", currentRange);
  }
}
