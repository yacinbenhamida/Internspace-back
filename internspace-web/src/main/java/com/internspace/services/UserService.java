package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.UserEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;
import com.internspace.entities.users.User;
@Path("users")
public class UserService {
	
	@Inject
	UserEJBLocal service; 
	
	// users related webservices 11/30/19
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") long idStudent)
	{
		User s = service.getUserById(idStudent);
		if(s != null) return Response.ok(s).status(200).build();
		return Response.ok("no records").status(404).build();
				
	}

	@GET
	@Path("/teachers/ofdepartment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeachersOfDept(@PathParam("id") long idDept)
	{
		List<Employee> employees=service.getTeachersOFdept(idDept);
		if(employees != null) return Response.ok(employees).status(200).build();
		return Response.ok("no records").status(404).build();
				
	}
	@GET
	@Path("/studentsBySheet/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentOfSHEET(@PathParam("id") long idStudent)
	{
		Student s = service.getStudentOfFypSheet(idStudent);
		if(s != null) return Response.ok(s).status(200).build();
		return Response.ok("no records").status(404).build();
				
	}
	
}
