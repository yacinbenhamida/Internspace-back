package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;
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

import com.internspace.ejb.FYPSheetEJB;
import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;

@Path("fypsheet")
public class FYPSheetService {
	@Inject
	FYPSheetEJBLocal fypSheetService;

	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFYPSheet(FYPFile file) {
		FYPFile res = fypSheetService.addFYPSheet(file);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFYPSById(@PathParam("id")long id) {
		FYPFile f =  fypSheetService.getFYFileById(id);
		if(f!=null) {
			return Response.status(Response.Status.OK).entity(f).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("notfound").build();
	}
	
	@GET
	@Path("student/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFYPFileOfStudent(@PathParam("id")long idStudent) {
		FYPFile res = fypSheetService.getFypFileOfStudent(idStudent);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("notfound").build();
	}
	
	@GET
	@Path("department/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFYPFileOfDepartment(@PathParam("id")long idDep) {
		List<FYPFile> res = fypSheetService.getFYPfilesOfDepartment(idDep);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("notfound").build();
	}
	
	@GET
	@Path("teacher/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFYPFileOfEmployee(@PathParam("id")long idTeacher) {
		List<FYPFile> res = fypSheetService.getFYPSheetsOfTeacher(idTeacher);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("notfound").build();
	}
	@DELETE
	@Path("delete/{ids}")
	public Response removeSheet(@PathParam("ids")long idSheet) {
		if(fypSheetService.removeFYPSheet(idSheet)) {
			return Response.status(Response.Status.OK).entity("deleted").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@GET 
	@Path("accepted")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAcceptedFYPSheets() {
		List<FYPFile> res = fypSheetService.getAllAcceptedFYPSheets();
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	@GET 
	@Path("nomarks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSheetsWithNoMarks() {
		List<FYPFile> res = fypSheetService.getAllSheetsWithNoMarks();
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	@GET 
	@Path("nosupervisors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFYPSheetsWithNoSupervisors() {
		List<FYPFile> res = fypSheetService.getFYPSheetsWithNoSupervisors();
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSheets() {
			return Response.status(Response.Status.OK).entity(fypSheetService.getAllSheets()).build();
	}
	
	
	
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response  addFile(FYPFile file,FYPFeature fyp) {
		
		fypSheetService.saisirFYPFile(file,fyp);
		return Response.status(Status.OK).entity(file).build() ;
		
		
	}
}
