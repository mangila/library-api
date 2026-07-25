package com.github.mangila.library.integration.jobrunr.job;

import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
import java.util.List;
import org.jobrunr.jobs.lambdas.JobRequest;

public record StagingProcessJobRequest(OpenLibraryType openLibraryType, int limit)
    implements JobRequest {

  public static final List<String> LABELS = List.of("openlibrary", "staging");

  @Override
  public Class<StagingProcessJobHandler> getJobRequestHandler() {
    return StagingProcessJobHandler.class;
  }
}
