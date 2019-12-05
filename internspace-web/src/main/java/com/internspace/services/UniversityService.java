package com.internspace.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.UniversityEJBLocal;
import com.internspace.entities.university.University;

@Path("university")
@Stateless
public class UniversityService {
	@Inject
	UniversityEJBLocal universityServices;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUniversity(University university) {
		int result = universityServices.addUniversity(university);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("Successfully added University: \n"+university.toString()).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUniversity(University university) {
		int result = universityServices.updateUniversity(university);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("Successfully updated University: \n"+university.toString()).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteUniversity(@PathParam(value="id") long id) {
		int result = universityServices.deleteUniversity(id);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("University removed.").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity("Could not delete id : "+id).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUniversities() {
		return Response.status(Response.Status.OK)
				.entity(universityServices.getAllUniversities()).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response searchUniversity(@PathParam(value="id") long id) {
		return Response.status(Response.Status.OK)
				.entity(universityServices.searchUniversity(id)).build();
		
	}
	
}
