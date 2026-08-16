package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.staging.StagingDataService;
import jakarta.enterprise.context.ApplicationScoped;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class StagingDeleteJobHandler implements JobRequestHandler<StagingDeleteJobRequest> {

  private final StagingDataService stagingDataService;

  public StagingDeleteJobHandler(StagingDataService stagingDataService) {
    this.stagingDataService = stagingDataService;
  }

  @Override
  public void run(StagingDeleteJobRequest jobRequest) {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final long rows = stagingDataService.deleteAllProcessed();
    jobContext.logger().info("Deleted rows: %s".formatted(rows));
  }
}
