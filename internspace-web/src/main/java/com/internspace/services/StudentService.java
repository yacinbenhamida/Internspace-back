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
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;
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
	@Path("enregistrer")
	public void enregistrerAuPlatforme(@QueryParam(value = "cin") String cin){
		Studentservice.enregistrerAuPlatforme(cin);
	};
	
	
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("isautorised")
	public List<Student> getstudentDisabled(){
		 return Studentservice.getAllStudentAutorised();
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
	public void sendMaill(@QueryParam(value = "text") String text,@QueryParam(value = "cin") String cin){
		Studentservice.sendMail(text,cin);
	};

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("lscin")
	public List<FYPFile> getstudentCin(){
		 return Studentservice.getAllStudentFile();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("ci")
	public List<FYPFile> getstudentCinfile(@QueryParam(value = "cin") String cin){
		 return Studentservice.getAllStudentFileCin(cin);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("maili")
	public void mailetat(@QueryParam(value = "text") String text,@QueryParam(value = "cin") String cin){
		Studentservice.mailEtat(text,cin);
		
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("si")
	public List<Student> getstudent(@QueryParam(value = "cin") String cin){
		 return Studentservice.getAllStudentCin(cin);
	};


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("directeur")
	public List<Employee> getDirector(@QueryParam(value = "cin") String cin){
		 return Studentservice.getDirector(cin);
	};
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pendingStudent")
	public List<FYPFile> getAllSheetsPendingStudent(){
		 return Studentservice.getAllSheetsPendingStudent();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pendingStudentId")
	public List<FYPFile> getAllSheetsPendingByStudent(@QueryParam(value = "cin") String cin){
		 return Studentservice.getAllSheetsPendingByStudent(cin);
	};
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("latestudents")
	public List<Student> getAllStudentLateYear(){
		 return Studentservice.getAllStudentLateYear();
	};
}
