package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import com.internspace.ejb.abstraction.CompanyEJBLocal;

import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.users.Company;

@Path("company")
@Stateless
public class CompanyService {

	@Inject
	CompanyEJBLocal service;
	
	@GET
	@Path("/subjects")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsBySite(
			@QueryParam("company") int companyId,
			@QueryParam("filter-untaken") boolean filteruntaken) {
		List<FYPSubject> subjects = service.getFypSubjectsByCompany(companyId, filteruntaken);

        return Response.ok(subjects).status(200)
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Max-Age", "1209600")
	        .build();
	}
	
	
@POST
@Path("add")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response  addComapny(Company company) {
	
		
		service.createCompany(company);
		return Response.status(Status.OK).entity(company).build();
	}
}
