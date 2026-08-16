package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.shared.LibraryType;
import com.github.mangila.library.staging.StagingDataService;
import com.github.mangila.library.staging.StagingEntity;
import com.github.mangila.library.staging.StagingRouter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class StagingProcessJobHandler implements JobRequestHandler<StagingProcessJobRequest> {

  private final StagingDataService stagingDataService;
  private final StagingRouter stagingRouter;

  public StagingProcessJobHandler(
      StagingDataService stagingDataService, StagingRouter stagingRouter) {
    this.stagingDataService = stagingDataService;
    this.stagingRouter = stagingRouter;
  }

  @Override
  @Transactional
  public void run(StagingProcessJobRequest jobRequest) {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final int limit = jobRequest.limit();
    final LibraryType libraryType = jobRequest.libraryType();
    final Class<?> clazz =
        switch (libraryType) {
          case AUTHOR -> Author.class;
          case EDITION -> throw new UnsupportedOperationException("Edition not supported");
          case WORK -> throw new UnsupportedOperationException("Work not supported");
        };
    final List<StagingEntity> stagingEntities = stagingDataService.findBy(libraryType, limit);
    if (stagingEntities.isEmpty()) {
      jobContext.logger().info("No staging entities found for: %s".formatted(libraryType));
      return;
    }
    final List<?> batch =
        stagingEntities.stream()
            .map(
                entity -> {
                  entity.setProcessed(true);
                  return stagingRouter.createNew(entity, libraryType, clazz);
                })
            .toList();
    stagingRouter.saveAll(batch, libraryType);
  }
}
