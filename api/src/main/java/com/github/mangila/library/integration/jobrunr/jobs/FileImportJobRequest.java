package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.shared.FilePath;
import io.github.mangila.ensure4j.Ensure;
import org.jobrunr.jobs.lambdas.JobRequest;

public record FileImportJobRequest(FilePath filePath) implements JobRequest {

  public FileImportJobRequest {
    Ensure.notNull(filePath);
  }

  @Override
  public Class<FileImportJobHandler> getJobRequestHandler() {
    return FileImportJobHandler.class;
  }
}
