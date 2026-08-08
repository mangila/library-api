package com.github.mangila.library.integration.jobrunr.jobs;

import org.jobrunr.jobs.lambdas.JobRequest;

public record DatabaseBackupJobRequest() implements JobRequest {

  @Override
  public Class<DatabaseBackupJobHandler> getJobRequestHandler() {
    return DatabaseBackupJobHandler.class;
  }
}
