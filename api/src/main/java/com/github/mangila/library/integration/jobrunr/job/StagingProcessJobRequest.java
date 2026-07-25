package com.github.mangila.library.integration.jobrunr.job;

import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
import org.jobrunr.jobs.lambdas.JobRequest;

public record StagingProcessJobRequest(OpenLibraryType openLibraryType, int limit)
    implements JobRequest {

  @Override
  public Class<StagingProcessJobHandler> getJobRequestHandler() {
    return StagingProcessJobHandler.class;
  }
}
