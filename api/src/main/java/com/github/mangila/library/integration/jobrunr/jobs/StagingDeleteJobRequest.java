package com.github.mangila.library.integration.jobrunr.jobs;

import org.jobrunr.jobs.lambdas.JobRequest;

public record StagingDeleteJobRequest() implements JobRequest {

  @Override
  public Class<StagingDeleteJobHandler> getJobRequestHandler() {
    return StagingDeleteJobHandler.class;
  }
}
