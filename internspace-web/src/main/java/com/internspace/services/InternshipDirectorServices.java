package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.university.CompanyCoordinates;
import com.internspace.entities.university.Departement;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.users.Company;
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
	public List<Student> getLastStudent(@QueryParam(value = "year") int year,@QueryParam(value = "id") long id){
		return service.getLateStudentsList(year,id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listmailing")
	public void sendMail(@QueryParam(value = "year") int year, @QueryParam(value = "text") String text,@QueryParam(value = "id") long id){
		 service.sendMail(year,text,id);
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
	@Path("PendingFYPFile")
	public List<Object[]> PendingFYPFile(){
		//here the pyth script will be RUN
		 return service.getPendingFYPFile();
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
	@Path("allFYPFileByCategory")
	public Response getFYPFileListByCategory(@QueryParam(value = "category")String category){
		 return Response.status(Status.OK).entity(service.getFYPFileListByCategory(category)).build();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allFYPFileBySpec")
	public List<FYPFile> FYPFileBySpecifiqueCrit(@QueryParam(value = "location") String location,@QueryParam(value = "year") int year ,@QueryParam(value = "state") FYPFileStatus state, @QueryParam(value = "category") String category){
		 return service.getFYPFileListSpecifique(year, location, state, category);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("currentFYPFile")
	public List<FYPFile> CurrentFYPFile(){
		FYPFileStatus state = FYPFileStatus.pending;
		 return service.getFYPFileListCurrentYear(state);
	};
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptFYPFileAnnulation")
	public void acceptFYPFileAnnulation(@QueryParam(value = "id") long id){
		service.acceptCancelingDemand(id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
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
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptFYPFile")
	public void acceptFYPFile(@QueryParam(value = "id") long id){
		service.acceptFile(id);
	};
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("refuseFYPFile")
	public void refuseFile(@QueryParam(value = "id") long id, @QueryParam(value = "text") String text){
		service.refuseFile(id, text);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("disableAccount")
	public void disableAccount(@QueryParam(value = "id") long id){
		service.disableAccount(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("allStudents")
	public List<Student> AllStudents(@QueryParam(value = "id") long id){
		return service.getAllStudentsList(id);
	};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("FindStudent")
	public  Student findStudent ( @QueryParam(value = "cin")String cin, @QueryParam(value = "id") long id){
		return service.FindStudent(cin,id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("FixActionNumberAsSupervisor")
	public void FixActionNumberAsSupervisor( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") long id){
		service.FixActionNumberAsSupervisor(nb, id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("FixActionNumberAsProtractor")
	public void FixActionNumberAsProtractor( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") long id){
		service.FixActionNumberAsProtractor(nb, id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("FixActionNumberAsPreValidator")
	public void FixActionNumberAsPreValidator( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") long id){
		service.FixActionNumberAsPreValidator(nb, id);
	};
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("FixActionNumberAsJuryPresident")
	public void FixActionNumberAsJuryPresident( @QueryParam(value = "nb") int nb , @QueryParam(value = "id") long id){
		service.FixActionNumberAsJuryPresident(nb, id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listDepartement")
	public List<Departement> DepartementList( @QueryParam(value = "id") long id){
		return service.getDepartements(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("departementInfo")
	public Departement DepartementInfo( @QueryParam(value = "id") long id){
		return service.getDepartementInfo(id);
	};
	
	
	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("AdrCompany")
	public Boolean CompanyAdr(@QueryParam(value = "id") long id ){
		return service.GetNameAndCountry(51);
	};*/
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("FullInfoOfStudent")
	public List<FYPSubject> myList( @QueryParam(value = "id") long id){
		return service.FullStudentInfoWithVerifiedCompanys(id);
	};
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("FYPFileToGetLinks")
	public List<Object[]>getFYPFileListWithCompanyLinks(){
		 return service.getPendingFYPFileWithLinks();
	};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getLinkOfCompany")
	public List<String>getLinksOfCompany(@QueryParam(value = "id") long id){
		 return service.GetLinksOfCompany(id);
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getCompanyCord")
	public  CompanyCoordinates getCompanyCord ( @QueryParam(value = "id") long id){
		return service.getCompanyCoordinates(id);
	};
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("departmentLists")
	public List<Departement> DepList( @QueryParam(value = "id") long id){
		return service.getDepartements(id);
	};
	/*******************************
	* Not the work of Mahmoud !!!! *
	********************************/
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptPFE")
	public Response acceptPFE(@QueryParam(value = "id") long id){
		Student res = service.acceptPFE(id);
		if(res!= null) {
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		}
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Student is not Saved").build();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptModifMajor")
	public Response acceptModification(@QueryParam(value = "id") long id){
		boolean res= service.acceptModification(id);
		if (res == true)
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		else
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("FypFile not exist or already confirmed").build();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptPFEe")
	public Response acceptPFEe(@QueryParam(value = "id") long id){
		FYPFile res = service.acceptPFEFyp(id);
		if(res!= null) {
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		}
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Student is not Saved").build();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cancelPFEe")
	public Response cancelPFEe(@QueryParam(value = "id") long id){
		FYPFile res = service.cancelPFEFyp(id);
		if(res!= null) {
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		}
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Student is not Saved").build();
	};
	
	
}
