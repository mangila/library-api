package com.github.mangila.library.integration.openlibrary;

import static org.apache.commons.io.FileUtils.ONE_MB;

import io.github.mangila.ensure4j.Ensure;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.function.BiConsumer;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

@ApplicationScoped
public class OpenLibraryDownloadIntegration {

  private static final int DOWNLOAD_BUFFER_SIZE = (int) (FileUtils.ONE_KB * 64);
  private static final int WRITE_BUFFER_SIZE = DOWNLOAD_BUFFER_SIZE * 4;

  private final OpenLibraryClient openLibraryClient;

  public OpenLibraryDownloadIntegration(@RestClient OpenLibraryClient openLibraryClient) {
    this.openLibraryClient = openLibraryClient;
  }

  public Path downloadToFileSystem(Path destination, BiConsumer<Long, Long> progress) {
    Ensure.notNull(destination);
    Ensure.notNull(progress);
    final Path fileName = Ensure.notNull(destination.getFileName());
    final String fileNameAsString = fileName.toString();
    final Path downloadDestination = destination.resolveSibling(fileName + ".tmp");
    final long contentLength = getContentLength(fileNameAsString);
    progress.accept(0L, contentLength);
    try (RestResponse<InputStream> response = openLibraryClient.download(fileNameAsString)) {
      Ensure.isTrue(response.getStatus() == 200, "Response status is not 200");
      try (InputStream in = response.getEntity();
          OutputStream out =
              Files.newOutputStream(
                  downloadDestination,
                  StandardOpenOption.WRITE,
                  StandardOpenOption.CREATE,
                  StandardOpenOption.TRUNCATE_EXISTING);
          BufferedOutputStream bos = new BufferedOutputStream(out, WRITE_BUFFER_SIZE)) {
        long nextProgressUpdate = ONE_MB;
        long transferred = 0;
        byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer, 0, DOWNLOAD_BUFFER_SIZE)) >= 0) {
          bos.write(buffer, 0, read);
          transferred += read;
          if (transferred >= nextProgressUpdate) {
            progress.accept(transferred, contentLength);
            nextProgressUpdate *= 2;
          }
        }
        bos.flush();
        Files.move(
            downloadDestination,
            destination,
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING);
      }
      return destination;
    } catch (IOException e) {
      throw new UncheckedIOException("Download FAIL: %s".formatted(fileName), e);
    } finally {
      try {
        Files.deleteIfExists(downloadDestination);
      } catch (IOException _) {
        // do nothing
      }
    }
  }

  private long getContentLength(String fileName) {
    try (Response response = openLibraryClient.checkDownload(fileName)) {
      Log.info(response.getHeaders());
      Ensure.isTrue(response.getStatus() == 200, "Response status is not 200");
      final String length = response.getHeaderString(HttpHeaderNames.CONTENT_LENGTH.toString());
      Ensure.notBlank(length, "Content-Length header is missing");
      final long contentLength = Long.parseLong(length);
      Ensure.positive(contentLength, "Content-Length header value is not positive");
      return contentLength;
    }
  }
}
