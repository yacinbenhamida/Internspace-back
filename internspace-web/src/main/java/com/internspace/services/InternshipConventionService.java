package com.internspace.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.InternshipConventionEJBLocal;
import com.internspace.entities.fyp.InternshipConvention;



@Path("intership")
@Stateless
public class InternshipConventionService {
	
	
	@Inject
	InternshipConventionEJBLocal internshipConvention;
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response  addIntershipConvention(InternshipConvention ic) {
		
		
		internshipConvention.addInternshipConvention(ic);
		return Response.status(Status.OK).entity(ic).build();
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInternshipConvention() {
		return Response.status(Status.OK)
				.entity(internshipConvention.getAllInternshipConvention()).build();
		
	
		
	}
	
	@DELETE
	@Path("delete/{id}")
	public Response deleteUniversity(@PathParam(value="id") long id) {
		int result = internshipConvention.removeConvention(id);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("University removed.").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity("Could not delete id : "+id).build();
	}
}
