package com.github.mangila.library.integration.jobrunr;

import org.jobrunr.jobs.JobId;

import java.util.UUID;

public record JobCreatedDto(UUID value) {

    public static JobCreatedDto from(JobId jobId) {
        final UUID asUuid = jobId.asUUID();
        return new JobCreatedDto(asUuid);
    }

}
