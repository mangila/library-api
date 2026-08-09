package com.github.mangila.library.integration.jobrunr.jobs.author;

import org.jobrunr.jobs.lambdas.JobRequest;

public record AuthorImportJobRequest(String fileName) implements JobRequest {

  @Override
  public Class<AuthorImportJobHandler> getJobRequestHandler() {
    return AuthorImportJobHandler.class;
  }
}
