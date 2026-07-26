package com.github.mangila.library.integration.jobrunr;

import org.jobrunr.jobs.lambdas.JobRequest;

public record OpenLibraryDownloadJobRequest(String fileName) implements JobRequest {

  @Override
  public Class<OpenLibraryDownloadJobHandler> getJobRequestHandler() {
    return OpenLibraryDownloadJobHandler.class;
  }
}
