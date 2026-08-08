package com.github.mangila.library.integration.jobrunr.jobs;

import jakarta.enterprise.context.ApplicationScoped;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

@ApplicationScoped
public class OpenLibraryEtlJobHandler implements JobRequestHandler<OpenLibraryEtlJobRequest> {

  @Override
  public void run(OpenLibraryEtlJobRequest jobRequest) throws Exception {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
