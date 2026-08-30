package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.author.domain.Author;
import com.github.mangila.library.shared.LibraryType;
import com.github.mangila.library.staging.StagingDataService;
import com.github.mangila.library.staging.StagingEntity;
import com.github.mangila.library.staging.StagingRouter;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.narayana.jta.BeginOptions;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Gatherers;
import java.util.stream.Stream;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class StagingProcessJobHandler implements JobRequestHandler<StagingProcessJobRequest> {

  private static final String BATCH_COUNT_METADATA_KEY = "batchCount";
  private static final String ROW_COUNT_METADATA_KEY = "rowCount";
  private static final String ERROR_COUNT_METADATA_KEY = "errorCount";

  private final StagingDataService stagingDataService;
  private final StagingRouter stagingRouter;

  public StagingProcessJobHandler(
      StagingDataService stagingDataService, StagingRouter stagingRouter) {
    this.stagingDataService = stagingDataService;
    this.stagingRouter = stagingRouter;
  }

  @Override
  @ActivateRequestContext
  public void run(StagingProcessJobRequest jobRequest) {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final int limit = jobRequest.limit();
    final LibraryType libraryType = jobRequest.libraryType();
    jobContext.saveMetadataIfAbsent(BATCH_COUNT_METADATA_KEY, 0);
    jobContext.saveMetadataIfAbsent(ROW_COUNT_METADATA_KEY, 0);
    jobContext.saveMetadataIfAbsent(ERROR_COUNT_METADATA_KEY, 0);
    final Class<?> clazz =
        switch (libraryType) {
          case AUTHOR -> Author.class;
          case EDITION -> throw new UnsupportedOperationException("Edition not supported");
          case WORK -> throw new UnsupportedOperationException("Work not supported");
        };
    final BeginOptions beginOptions = QuarkusTransaction.beginOptions();
    final int transactionTimeout = Math.toIntExact(Duration.ofMinutes(10).toSeconds());
    beginOptions.timeout(transactionTimeout);
    QuarkusTransaction.begin(beginOptions);
    final AtomicInteger batchCount =
        new AtomicInteger(jobContext.getMetadata(BATCH_COUNT_METADATA_KEY));
    final AtomicInteger rowCount =
        new AtomicInteger(jobContext.getMetadata(ROW_COUNT_METADATA_KEY));
    final AtomicInteger errorCount =
        new AtomicInteger(jobContext.getMetadata(ERROR_COUNT_METADATA_KEY));
    try (Stream<StagingEntity> stream = stagingDataService.streamByType(libraryType)) {
      stream
          .gather(Gatherers.windowFixed(limit))
          .forEach(
              batch -> {
                int currentRowCount = rowCount.addAndGet(batch.size());
                int currentBatchCount = batchCount.incrementAndGet();
                if (currentBatchCount % 100 == 0) {
                  jobContext
                      .logger()
                      .info(
                          """
                          Rows: %s
                          Batches: %s
                          Errors: %s
                          """
                              .formatted(currentRowCount, currentBatchCount, errorCount.get()));
                }
                try {
                  QuarkusTransaction.requiringNew()
                      .run(
                          () -> {
                            final List<?> entities =
                                batch.stream()
                                    .map(
                                        stagingEntity ->
                                            stagingRouter.createNew(
                                                stagingEntity, libraryType, clazz))
                                    .toList();
                            stagingRouter.saveAll(entities, libraryType);
                            stagingDataService.updateProcessed(batch);
                          });
                  Panache.getEntityManager().clear();
                } catch (Exception e) {
                  jobContext.saveMetadata(BATCH_COUNT_METADATA_KEY, currentBatchCount);
                  jobContext.saveMetadata(ROW_COUNT_METADATA_KEY, currentRowCount);
                  jobContext.saveMetadata(ERROR_COUNT_METADATA_KEY, errorCount.incrementAndGet());
                  jobContext.logger().error("ERR: %s".formatted(e.getMessage()));
                }
              });
      QuarkusTransaction.commit();
    } catch (Exception e) {
      jobContext.saveMetadata(BATCH_COUNT_METADATA_KEY, batchCount.get());
      jobContext.saveMetadata(ROW_COUNT_METADATA_KEY, rowCount.get());
      jobContext.saveMetadata(ERROR_COUNT_METADATA_KEY, errorCount.incrementAndGet());
      QuarkusTransaction.rollback();
      throw e;
    }
  }
}
