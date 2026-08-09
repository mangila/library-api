package com.github.mangila.library.integration.jobrunr.jobs.author;

import java.util.LinkedHashMap;
import org.jobrunr.jobs.lambdas.JobRequest;

public record AuthorProcessJobRequest(LinkedHashMap<String, String> csvRecord)
    implements JobRequest {
  @Override
  public Class<AuthorProcessJobHandler> getJobRequestHandler() {
    return AuthorProcessJobHandler.class;
  }
}
