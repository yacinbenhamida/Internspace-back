package com.internspace.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.StudentEJBLocal;

@Path("student")
@Stateless
public class StudentService {
	
	@Inject
	StudentEJBLocal Student;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK)
				.entity(Student.getAll()).build();
		
	
		
	}

}
