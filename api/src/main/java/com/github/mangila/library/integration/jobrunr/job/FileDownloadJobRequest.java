package com.github.mangila.library.integration.jobrunr.job;

import com.github.mangila.library.shared.FilePath;
import io.github.mangila.ensure4j.Ensure;
import java.util.List;
import org.jobrunr.jobs.lambdas.JobRequest;

public record FileDownloadJobRequest(FilePath filePath) implements JobRequest {

  public static final List<String> LABELS = List.of("openlibrary", "download");

  public FileDownloadJobRequest {
    Ensure.notNull(filePath);
  }

  @Override
  public Class<FileDownloadJobHandler> getJobRequestHandler() {
    return FileDownloadJobHandler.class;
  }
}
