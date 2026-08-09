package com.github.mangila.library.integration.jobrunr.jobs.author;

import com.github.mangila.library.integration.jobrunr.JobRunrScheduler;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Gatherers;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class AuthorImportJobHandler implements JobRequestHandler<AuthorImportJobRequest> {

  private static final int READ_BUFFER_SIZE = (int) FileUtils.ONE_KB * 128;
  private static final int BATCH_SIZE = 1024;

  private final JobRunrScheduler jobRunrScheduler;

  public AuthorImportJobHandler(JobRunrScheduler jobRunrScheduler) {
    this.jobRunrScheduler = jobRunrScheduler;
  }

  @Override
  public void run(AuthorImportJobRequest jobRequest) {
    final String fileName = jobRequest.fileName();
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final Path dataDir = Path.of("data");
    final Path destination = dataDir.resolve(fileName);
    if (!Files.exists(destination)) {
      jobContext.logger().info("File does not exist: %s".formatted(destination));
      return;
    }
    jobContext.logger().info("Processing file: %s".formatted(destination));
    CSVFormat format =
        CSVFormat.TDF
            .builder()
            .setHeader("type", "key", "revision", "last_modified", "JSON")
            .setIgnoreEmptyLines(true)
            .setSkipHeaderRecord(true)
            .setTrim(true)
            .get();
    try (final InputStream inputStream = Files.newInputStream(destination);
        final GZIPInputStream gzip = new GZIPInputStream(inputStream, READ_BUFFER_SIZE);
        final InputStreamReader decoder = new InputStreamReader(gzip, StandardCharsets.UTF_8);
        final CSVParser csvParser = format.parse(decoder);
        final Stream<CSVRecord> authorCsvRecordStream = csvParser.stream()) {
      authorCsvRecordStream
          .gather(Gatherers.windowFixed(BATCH_SIZE))
          .forEach(jobRunrScheduler::enqueueAuthorProcess);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
