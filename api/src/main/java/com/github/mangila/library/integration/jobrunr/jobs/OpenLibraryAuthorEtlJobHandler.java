package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.author.domain.AuthorService;
import com.github.mangila.library.author.shared.AuthorMapper;
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
public class OpenLibraryAuthorEtlJobHandler
    implements JobRequestHandler<OpenLibraryAuthorEtlJobRequest> {

  private static final int READ_BUFFER_SIZE = (int) FileUtils.ONE_KB * 64;

  private final AuthorMapper authorMapper;
  private final AuthorService authorService;

  public OpenLibraryAuthorEtlJobHandler(AuthorMapper authorMapper, AuthorService authorService) {
    this.authorMapper = authorMapper;
    this.authorService = authorService;
  }

  @Override
  public void run(OpenLibraryAuthorEtlJobRequest jobRequest) {
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
    CSVParser.Builder parser =
        CSVParser.builder().setFormat(format).setBufferSize(READ_BUFFER_SIZE);
    try (final InputStream inputStream = Files.newInputStream(destination);
        final GZIPInputStream gzip = new GZIPInputStream(inputStream, READ_BUFFER_SIZE);
        final InputStreamReader decoder = new InputStreamReader(gzip, StandardCharsets.UTF_8);
        final CSVParser csvParser = parser.setReader(decoder).get();
        final Stream<CSVRecord> lines = csvParser.stream()) {
      jobContext.logger().info(csvParser.getHeaderNames().toString());
      lines
          .map(authorMapper::toDomain)
          .gather(Gatherers.windowFixed(250))
          .forEach(authorService::saveAll);

    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
