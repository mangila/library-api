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
  public RestResponse<JobScheduledDto> scheduleDownload(@PathParam("fileName") String fileName) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleFileDownload(fileName);
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/import/{fileName}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleImport(@PathParam("fileName") String fileName) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleFileImport(fileName);
    return RestResponse.ok(jobScheduledDto);
  }
}
