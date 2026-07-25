package com.github.mangila.library.integration.jobrunr.rest;

import com.github.mangila.library.integration.openlibrary.OpenLibraryType;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.annotation.security.RolesAllowed;
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
  @RolesAllowed({"ADMIN"})
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleFileDownload(
      @PathParam("type") OpenLibraryType type) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleFileDownload(type);
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/import/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RolesAllowed({"ADMIN"})
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleFileImport(@PathParam("type") OpenLibraryType type) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleFileImport(type);
    return RestResponse.ok(jobScheduledDto);
  }

  @Path("/process/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  @GET
  @RolesAllowed({"ADMIN"})
  @RunOnVirtualThread
  public RestResponse<JobScheduledDto> scheduleStagingProcessing(
      @PathParam("type") OpenLibraryType type, @QueryParam("limit") @Max(5000) int limit) {
    JobScheduledDto jobScheduledDto = jobRunrRestService.scheduleStagingProcessing(type, limit);
    return RestResponse.ok(jobScheduledDto);
  }
}
