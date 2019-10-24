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
import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.Internship;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.users.Student;

@Path("internship")
@Stateless
public class InternshipDirectorServices {
	
	
	@Inject
	InternshipDirectorEJBLocal service;
	@Inject
	StudentEJBLocal Studentservice;
	
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
	@Path("allFYPFileByYear")
	public List<FYPFile>getFYPFileListByYear(@QueryParam(value = "year")int year){
		 return service.getFYPFileListByYear(year);
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptFYPFileAnnulation")
	public void acceptFYPFileAnnulation(@QueryParam(value = "id") long id){
		service.acceptCancelingDemand(id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("declineFYPFileAnnulation")
	public void declineFYPFileAnnulation(@QueryParam(value = "id") long id, @QueryParam(value = "text") String text){
		service.declineCancelingDemand(id, text);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("FYPFileAnnulationDemandeList")
	public  List<FYPFile> FYPFileAnnulationDemandeList(){
		return service.listCancelingDemand();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptFYPFile")
	public void acceptFYPFile(@QueryParam(value = "id") long id){
		service.acceptFile(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("refuseFYPFile")
	public void refuseFile(@QueryParam(value = "id") long id, @QueryParam(value = "text") String text){
		service.refuseFile(id, text);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("disableAccount")
	public void disableAccount(@QueryParam(value = "id") long id){
		service.disableAccount(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allStudents")
	public List<Student> AllStudents(){
		return service.getAllStudentsList();
	};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("FindStudent")
	public  Student findStudent ( @QueryParam(value = "id")long id){
		return service.FindStudent(id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("ValidateSubmittedAReport")
	public void ValidateSubmittedAReport(@QueryParam(value = "id") long id){
		service.ValidateSubmittedAReport(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("WaitingForDefensePlanningList")
	public  List<FYPFile> WaitingForDefensePlanningList (){
		return service.WaitingForDefensePlanningList();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("FilterWaitingForDefensePlanningList")
	public  FYPFile FilterWaitingForDefensePlanningList (@QueryParam(value = "cin") String cin, @QueryParam(value = "nom") String nom){
		return service.FilterWaitingForDefensePlanningList(cin,nom);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("FixActionNumberAsSupervisor")
	public void FixActionNumberAsSupervisor( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") int id){
		service.FixActionNumberAsSupervisor(nb, id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("FixActionNumberAsProtractor")
	public void FixActionNumberAsProtractor( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") int id){
		service.FixActionNumberAsProtractor(nb, id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("FixActionNumberAsPreValidator")
	public void FixActionNumberAsPreValidator( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") int id){
		service.FixActionNumberAsPreValidator(nb, id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("FixActionNumberAsJuryPresident")
	public void FixActionNumberAsJuryPresident( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") int id){
		service.FixActionNumberAsJuryPresident(nb, id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptPFE")
	public void acceptPFE(@QueryParam(value = "id") long id){
		service.acceptPFE(id);
	};
}
