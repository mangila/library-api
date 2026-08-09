package com.github.mangila.library.integration.jobrunr;

import java.util.UUID;
import org.jobrunr.jobs.JobId;

public record JobScheduledDto(UUID value) {

  public static JobScheduledDto from(JobId jobId) {
    final UUID asUuid = jobId.asUUID();
    return new JobScheduledDto(asUuid);
  }
}
