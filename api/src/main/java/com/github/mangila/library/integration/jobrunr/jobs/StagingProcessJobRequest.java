package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.shared.LibraryType;
import org.jobrunr.jobs.lambdas.JobRequest;

public record StagingProcessJobRequest(LibraryType libraryType, int limit) implements JobRequest {

  @Override
  public Class<StagingProcessJobHandler> getJobRequestHandler() {
    return StagingProcessJobHandler.class;
  }
}
