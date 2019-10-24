package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.StudentEJBLocal;

import com.internspace.entities.users.Student;

@Path("student")
@Stateless
public class StudentService {
	
	@Inject
	StudentEJBLocal Studentservice;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK)
				.entity(Studentservice.getAll()).build();
		
	
		
	}
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response  addStudent(Student std) {
		
		
		Studentservice.addStudent(std);
		return Response.status(Status.OK).entity(std).build();
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("issaved")
	public List<Student> getstudentCreated(){
		 return Studentservice.getAllStudentSaved();
	};


	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login")
	public void login(@QueryParam(value = "cin") String cin){
		Studentservice.login(cin);
	};
	
	
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isautorised")
	public List<Student> getstudentDisabled(){
		 return Studentservice.getAllStudentdisabled();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isnonautorised")
	public List<Student> getstudentNonDisabled(){
		 return Studentservice.getAllStudentNodisabled();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mail")
	public void sendMail(@QueryParam(value = "text") String text,@QueryParam(value = "cin") String cin){
		Studentservice.sendMail(text,cin);
	};

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("lscin")
	public List<Student> getstudentCin(){
		 return Studentservice.getAllStudentCIN();
	};
	
	
	
	
}
