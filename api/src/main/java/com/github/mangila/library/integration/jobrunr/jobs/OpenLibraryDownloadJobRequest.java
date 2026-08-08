package com.github.mangila.library.integration.jobrunr.jobs;

import io.github.mangila.ensure4j.Ensure;
import org.jobrunr.jobs.lambdas.JobRequest;

public record OpenLibraryDownloadJobRequest(String fileName) implements JobRequest {

  public OpenLibraryDownloadJobRequest {
    Ensure.notBlank(fileName);
  }

  @Override
  public Class<OpenLibraryDownloadJobHandler> getJobRequestHandler() {
    return OpenLibraryDownloadJobHandler.class;
  }
}
