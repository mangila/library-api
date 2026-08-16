package com.github.mangila.library.integration.jobrunr;

import com.github.mangila.library.shared.LibraryType;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

@Path("api/v1/jobrunr")
public class JobRunrRestResource {

  private final JobRunrRestService jobRunrRestService;

  public JobRunrRestResource(JobRunrRestService jobRunrRestService) {
    this.jobRunrRestService = jobRunrRestService;
  }

  @Path("/download/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleFileDownload(@PathParam("type") LibraryType type) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleFileDownload(type);
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/import/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleFileImport(@PathParam("type") LibraryType type) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleFileImport(type);
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/process/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleStagingProcessing(
      @PathParam("type") LibraryType type, @QueryParam("limit") @Max(5000) int limit) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleStagingProcessing(type, limit);
    return RestResponse.ok(jobScheduledDto);
  }
}
