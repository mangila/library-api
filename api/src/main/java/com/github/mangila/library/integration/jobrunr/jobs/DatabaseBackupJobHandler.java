package com.github.mangila.library.integration.jobrunr.jobs;

import com.github.mangila.library.appliction.DatabaseBackupService;
import jakarta.enterprise.context.ApplicationScoped;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.jobrunr.server.runner.ThreadLocalJobContext;

@ApplicationScoped
public class DatabaseBackupJobHandler implements JobRequestHandler<DatabaseBackupJobRequest> {

  private final DatabaseBackupService databaseBackupService;

  public DatabaseBackupJobHandler(DatabaseBackupService databaseBackupService) {
    this.databaseBackupService = databaseBackupService;
  }

  @Override
  public void run(DatabaseBackupJobRequest jobRequest) {
    final JobContext jobContext = ThreadLocalJobContext.getJobContext();
    final Path dbFile = databaseBackupService.dbFile();
    final long fileSize = databaseBackupService.size();
    jobContext
        .logger()
        .info(
            "Database file: %s - Size: %s"
                .formatted(dbFile, FileUtils.byteCountToDisplaySize(fileSize)));
    databaseBackupService.createBackup();
  }
}
