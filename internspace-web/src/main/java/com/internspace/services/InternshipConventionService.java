package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.InternshipConventionEJBLocal;
import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.users.Student;



@Path("intership")
@Stateless
public class InternshipConventionService {
	
	
	@Inject
	InternshipConventionEJBLocal internshipConvention;
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response  addIntershipConvention(InternshipConvention ic,@QueryParam(value = "id") long id) {
		
		
		internshipConvention.addInternshipConvention(ic,id);
		return Response.status(Status.OK).entity(ic).build();
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInternshipConvention() {
		return Response.status(Status.OK)
				.entity(internshipConvention.getAllInternshipConvention()).build();
		
	
		
	}
	
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getStudentInternshipConvention(@QueryParam(value = "id") long id) {
		
		 return internshipConvention.getFypConventionStudent(id);
	
		
	}
	
	@DELETE
	@Path("delete/{id}")
	public Response deleteUniversity(@PathParam(value="id") long id) {
		int result = internshipConvention.removeConvention(id);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("demande de stage est annulée.").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity("Could not delete id : "+id).build();
	}
}
