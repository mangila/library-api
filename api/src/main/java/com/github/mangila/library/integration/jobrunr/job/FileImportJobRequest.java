package com.github.mangila.library.integration.jobrunr.job;

import com.github.mangila.library.shared.FilePath;
import io.github.mangila.ensure4j.Ensure;
import java.util.List;
import org.jobrunr.jobs.lambdas.JobRequest;

public record FileImportJobRequest(FilePath filePath) implements JobRequest {

  public static final List<String> LABELS = List.of("openlibrary", "import");

  public FileImportJobRequest {
    Ensure.notNull(filePath);
  }

  @Override
  public Class<FileImportJobHandler> getJobRequestHandler() {
    return FileImportJobHandler.class;
  }
}
