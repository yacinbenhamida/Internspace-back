package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.users.Student;

@Path("internship")
@Stateless
public class InternshipDirectorServices {
	
	
	@Inject
	InternshipDirectorEJBLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listLate")
	public List<Student> getLastStudent(@QueryParam(value = "year") int year){
		return service.getLateStudentsList(year);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listmailing")
	public void sendMail(@QueryParam(value = "year") int year, @QueryParam(value = "text") String text){
		 service.sendMail(year,text);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allFYPFile")
	public List<FYPFile> allFYOFile(){
		 return service.getAllFYPFileList();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allFYPFileState")
	public List<FYPFile> FYPFileByState(@QueryParam(value = "state") FYPFileStatus state){
		 return service.getFYPFileListByState(state);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allFYPFileCountry")
	public List<FYPFile> FYPFileByCountry(@QueryParam(value = "location") String location){
		 return service.getFYPFileListByCountry(location);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allFYPFileBySpec")
	public List<FYPFile> FYPFileBySpecifiqueCrit(@QueryParam(value = "location") String location,@QueryParam(value = "year") int year ,@QueryParam(value = "state") FYPFileStatus state){
		 return service.getFYPFileListSpecifique(year, location, state);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("currentFYPFile")
	public List<FYPFile> CurrentFYPFile(){
		FYPFileStatus state = FYPFileStatus.pending;
		 return service.getFYPFileListCurrentYear(state);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("acceptFYPFileAnnulation")
	public void acceptFYPFileAnnulation(@QueryParam(value = "id") int id){
		service.acceptCancelingDemand(id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("declineFYPFileAnnulation")
	public void declineFYPFileAnnulation(@QueryParam(value = "id") int id, @QueryParam(value = "text") String text){
		service.declineCancelingDemand(id, text);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("annulledFYPFileList")
	public  List<FYPFile> annulledFYPFileList(){
		return service.listCancelingDemand();
	};
	
	
	

}
