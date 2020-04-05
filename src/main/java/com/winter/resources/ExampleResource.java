package com.winter.resources;

import com.winter.model.database.entity.CompanyStatistic;
import com.winter.model.database.entity.ProjectStatistic;
import com.winter.model.service.CompanyStatisticService;
import com.winter.model.service.ProjectStatisticService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/hello")
public class ExampleResource {

    @Inject
    ProjectStatisticService projectStatisticService;
    @Inject
    CompanyStatisticService companyStatisticService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        List<ProjectStatistic> top =  projectStatisticService.getTopN(10);
        return Response.ok(top).build();
    }
}