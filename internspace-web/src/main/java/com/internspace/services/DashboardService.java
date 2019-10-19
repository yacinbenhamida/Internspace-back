package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;

import com.internspace.ejb.abstraction.DashboardEJBLocal;
import com.internspace.entities.users.Student;

@Path("dashboard")
@Stateless
public class DashboardService {

	@Inject
	DashboardEJBLocal service;

	@GET
	@Path("/internship/distribution")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsLocationDistribution(
			@QueryParam("uni") int uniId, 
			@QueryParam("abroad") boolean abroad) {
		List<Student> students = service.getStudentsLocationDistribution(uniId, abroad);
        if (!students.isEmpty()) {
            return Response.ok(students).status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    //.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    //.header("Access-Control-Allow-Credentials", "true")
                    //.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
        }
        
        return Response.status(Response.Status.NOT_FOUND).build();
        
	}

}
