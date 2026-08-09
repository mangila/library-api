package com.github.mangila.library.integration.jobrunr.jobs;

import io.github.mangila.ensure4j.Ensure;
import org.jobrunr.jobs.lambdas.JobRequest;

public record FileDownloadJobRequest(String fileName) implements JobRequest {

  public FileDownloadJobRequest {
    Ensure.notBlank(fileName);
  }

  @Override
  public Class<FileDownloadJobHandler> getJobRequestHandler() {
    return FileDownloadJobHandler.class;
  }
}
