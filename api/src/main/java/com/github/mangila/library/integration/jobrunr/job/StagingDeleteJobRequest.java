package com.github.mangila.library.integration.jobrunr.job;

import java.util.List;
import org.jobrunr.jobs.lambdas.JobRequest;

public record StagingDeleteJobRequest() implements JobRequest {

  public static final List<String> LABELS = List.of("openlibrary", "delete");

  @Override
  public Class<StagingDeleteJobHandler> getJobRequestHandler() {
    return StagingDeleteJobHandler.class;
  }
}
