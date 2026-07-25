package com.github.mangila.library.integration.jobrunr.job;

import com.github.mangila.library.staging.StagingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class StagingDeleteJobHandler implements JobRequestHandler<StagingDeleteJobRequest> {

  private final StagingService stagingService;

  public StagingDeleteJobHandler(StagingService stagingService) {
    this.stagingService = stagingService;
  }

  @Override
  @Transactional
  public void run(StagingDeleteJobRequest jobRequest) {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final long rows = stagingService.deleteAllByProcessed(true);
    jobContext.logger().info("Deleted rows: %s".formatted(rows));
  }
}
