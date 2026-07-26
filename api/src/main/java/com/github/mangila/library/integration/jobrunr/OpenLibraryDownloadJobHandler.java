package com.github.mangila.library.integration.jobrunr;

import com.github.mangila.library.integration.openlibrary.OpenLibraryClient;
import io.github.mangila.ensure4j.Ensure;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class OpenLibraryDownloadJobHandler
    implements JobRequestHandler<OpenLibraryDownloadJobRequest> {

  private static final int DOWNLOAD_BUFFER_SIZE_128_KB = 128 * 1024;
  private static final int ONE_MB = 1024 * 1024;

  private final OpenLibraryClient openLibraryClient;

  @Inject
  public OpenLibraryDownloadJobHandler(@RestClient OpenLibraryClient openLibraryClient) {
    this.openLibraryClient = openLibraryClient;
  }

  @Override
  public void run(OpenLibraryDownloadJobRequest jobRequest) {
    final String fileName = jobRequest.fileName();
    final JobContext ctx = ThreadLocalJobContext.getJobContext();
    Path dataDir = Path.of("data");
    Path finalDestination = dataDir.resolve(fileName);
    Path downloadDestination = dataDir.resolve(fileName + ".tmp");

    try (RestResponse<InputStream> response = openLibraryClient.downloadDump(fileName)) {
      ctx.logger().info(response.getStringHeaders().toString());
      Ensure.isTrue(response.getStatus() == 200, "Response status is not 200");
      String contentLengthAsString =
          response.getHeaderString(HttpHeaderNames.CONTENT_LENGTH.toString());
      long contentLength = Long.parseLong(contentLengthAsString);
      if (contentLength > 0) {
        ctx.logger()
            .info(
                String.format(
                    "File size for %s: %.2f MB", fileName, (double) contentLength / ONE_MB));
      }

      try (InputStream in = response.getEntity();
          OutputStream out =
              Files.newOutputStream(
                  downloadDestination,
                  StandardOpenOption.WRITE,
                  StandardOpenOption.CREATE,
                  StandardOpenOption.TRUNCATE_EXISTING)) {
        final JobDashboardProgressBar jobDashboardProgressBar = ctx.progressBar(contentLength);
        long nextProgressUpdate = ONE_MB;
        long transferred = 0;
        byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE_128_KB];
        int read;
        while ((read = in.read(buffer, 0, DOWNLOAD_BUFFER_SIZE_128_KB)) >= 0) {
          out.write(buffer, 0, read);
          transferred += read;
          if (transferred >= nextProgressUpdate) {
            double megabytes = (double) transferred / (ONE_MB);
            ctx.logger().info(String.format("%.2f MB received", megabytes));
            jobDashboardProgressBar.setProgress(transferred);
            nextProgressUpdate *= 2;
          }
        }
        Files.move(
            downloadDestination,
            finalDestination,
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING);
        Log.infof("Successfully downloaded %s", finalDestination.toAbsolutePath());
      }

    } catch (IOException e) {
      try {
        Files.deleteIfExists(downloadDestination);
      } catch (IOException _) {
        // do nothing
      }
      throw new UncheckedIOException("Failed to download dump file: " + fileName, e);
    }
  }
}
