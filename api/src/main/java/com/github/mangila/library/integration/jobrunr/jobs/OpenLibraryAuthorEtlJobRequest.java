package com.github.mangila.library.integration.jobrunr.jobs;

import org.jobrunr.jobs.lambdas.JobRequest;

public record OpenLibraryAuthorEtlJobRequest(String fileName) implements JobRequest {

  @Override
  public Class<OpenLibraryAuthorEtlJobHandler> getJobRequestHandler() {
    return OpenLibraryAuthorEtlJobHandler.class;
  }
}
