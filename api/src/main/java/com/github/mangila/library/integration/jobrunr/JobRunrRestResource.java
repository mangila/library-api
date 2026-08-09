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

  @Path("/backup")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleBackup() {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleBackup();
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/download/{fileName}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleDownload(@PathParam("fileName") String fileName) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleOpenLibraryDownload(fileName);
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/etl/{fileName}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleEtl(@PathParam("fileName") String fileName) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleEtl(fileName);
    return RestResponse.ok(jobScheduledDto);
  }
}
