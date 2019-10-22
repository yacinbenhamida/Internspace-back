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
	@Path("iscreated")
	public List<Student> getstudentCreated(){
		 return Studentservice.getAllStudentCreated();
	};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enregistrer")
	public void enregistrer(@QueryParam(value = "id") long id){
		Studentservice.enregistrer(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptPFE")
	public void acceptPFE(@QueryParam(value = "id") long id){
		Studentservice.acceptPFE(id);
	};
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isdisabled")
	public List<Student> getstudentDisabled(){
		 return Studentservice.getAllStudentdisabled();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isnondisabled")
	public List<Student> getstudentNonDisabled(){
		 return Studentservice.getAllStudentNodisabled();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mail")
	public void sendMail(@QueryParam(value = "text") String text){
		Studentservice.sendMail(text);
	};

}
