package com.github.mangila.library.integration.jobrunr;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("api/v1/jobrunr")
public class JobRunrRestResource {

  private final JobRunrRestService jobRunrRestService;

  public JobRunrRestResource(JobRunrRestService jobRunrRestService) {
    this.jobRunrRestService = jobRunrRestService;
  }

  @Path("/download/{fileName}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobCreatedDto> findById(@PathParam("fileName") String fileName) {
    JobCreatedDto jobCreatedDto = jobRunrRestService.scheduleDownload(fileName);
    return RestResponse.ok(jobCreatedDto);
  }
}
