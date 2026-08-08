package com.github.mangila.library.integration.jobrunr;

import java.util.UUID;
import org.jobrunr.jobs.JobId;

public record JobCreatedDto(UUID value) {

  public static JobCreatedDto from(JobId jobId) {
    final UUID asUuid = jobId.asUUID();
    return new JobCreatedDto(asUuid);
  }
}
