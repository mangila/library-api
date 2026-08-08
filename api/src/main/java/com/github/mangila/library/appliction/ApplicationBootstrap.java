package com.github.mangila.library.appliction;

import com.github.mangila.library.integration.jobrunr.JobRunrScheduler;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Observes;

@Dependent
public class ApplicationBootstrap {

  public void boostrap(@Observes StartupEvent event, JobRunrScheduler jobRunrScheduler) {
    final String id = jobRunrScheduler.databaseBackupRecurringJob();
    Log.infof("Scheduled database backup job: %s", id);
  }

  @PreDestroy
  void afterBootstrap() {
    Log.info("Application bootstrap completed successfully.");
  }
}
