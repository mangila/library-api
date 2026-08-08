package com.github.mangila.library.integration.jobrunr.jobs;

import org.jobrunr.jobs.lambdas.JobRequest;

public record OpenLibraryEtlJobRequest(String fileName) implements JobRequest {

  @Override
  public Class<OpenLibraryEtlJobHandler> getJobRequestHandler() {
    return OpenLibraryEtlJobHandler.class;
  }
}
