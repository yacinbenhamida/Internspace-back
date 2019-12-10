package com.internspace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.users.Student;

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
	
	@PUT
	@Path("edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editFYPSheet(FYPFile file) {
		FYPFile res = fypSheetService.editFYPSheet(file);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@PUT
	@Path("assignSheet/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignFYPSheetToStudent(FYPFile file,@PathParam("studentId")long studentId) {
		FYPFile res = fypSheetService.assignFYPFileToStudent(file, studentId);
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
	@Path("accepted/{idDep}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAcceptedFYPSheets(@PathParam("idDep")long idDep) {
		List<FYPFile> res = fypSheetService.getAllAcceptedFYPSheets(idDep);
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
	@Path("nosupervisors/{idDep}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFYPSheetsWithNoSupervisors(@PathParam("idDep")long idDep) {
		List<FYPFile> res = fypSheetService.getFYPSheetsWithNoSupervisors(idDep);
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
	@GET
	@Path("ofdepartment/{depId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSheetsOfDepartment(@PathParam("depId")int depId) {
			List<FYPFile> res = fypSheetService.getFYPfilesOfDepartment(depId);
			return Response.status(Response.Status.OK).entity(res.toArray()).build();
	}
	
	@GET
	@Path("confirmed")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAcceptedSheets() {
		if(fypSheetService.getAllSheets()!=null) {
			List<FYPFile> sheets = fypSheetService.getAllSheets().stream()
					.filter(x->x.getIsConfirmed()).collect(Collectors.toList());	
			if(sheets!=null) {
				return Response.status(Response.Status.OK).entity(sheets).build();
			}
			}
			return Response.status(Response.Status.OK).entity("norecords").build();
	}
	
	@GET
	@Path("pending")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSheetsPending() {
			return Response.status(Response.Status.OK).entity(fypSheetService.getAllSheetsPending()).build();
	}
	@GET
	@Path("withNoMarks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allFYPfilesWatingForMarkFrom() {
		return Response.status(Response.Status.OK).entity(fypSheetService.allFYPfilesWatingForMarkFrom()).build();
	}
	
	
	// consulter l'etat de sa fichePFE et l'envoi d'un mail
	@GET
	@Path("etat")
	@Produces(MediaType.APPLICATION_JSON)
	public Response etatChanged(@QueryParam(value = "id") long id) {
		FYPFileStatus ff = fypSheetService.etatChanged(id);
		return 	Response.status(Response.Status.OK).entity(ff).build();
	}
	// consulter modif mineur ou major
	
	
	@PUT
	@Path("major")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modificationMajeur(@QueryParam(value = "id") long id) {
		 boolean res = fypSheetService.modificationMajeur(id);
		return 	Response.status(Response.Status.OK).entity("Modification major"+ res).build();
	}

	
	@PUT
	@Path("editt")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FYPFile editFYPSheett(FYPFile file) {
		 FYPFile res = fypSheetService.editFYPSheett(file);
		 return res;
		//	return Response.status(Response.Status.OK).entity("Modification Major").build();
		
		
	}
	@PUT
	@Path("edittstd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FYPFile editFYPSheetStudent(FYPFile file,@QueryParam(value = "id") long id) {
		 FYPFile res = fypSheetService.editFYPSheetStudent(file,id);
		 return res;
		//	return Response.status(Response.Status.OK).entity("Modification Major").build();
		
		
	}
	
	@PUT
	@Path("edittstdd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FYPFile editFYPSheetStudentMaj(FYPFile file,@QueryParam(value = "id") long id) {
		 
		 return fypSheetService.editFYPSheetStudentMaj(file,id);
		//	return Response.status(Response.Status.OK).entity("Modification Major").build();
		
		
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptPFE")
	public Response acceptPFE(@QueryParam(value = "id") long id){
		FYPFile res = fypSheetService.acceptPFE(id);
		if(res!= null) {
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		}
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Student is not Saved").build();
	};
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("acceptModif")
	public Response accept(@QueryParam(value = "id") long id){
		FYPFile res = fypSheetService.accept(id);
		if(res!= null) {
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		}
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Student is not Saved").build();
	};
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("cancelModif")
	public Response cancel(@QueryParam(value = "id") long id){
		FYPFile res = fypSheetService.cancel(id);
		if(res!= null) {
		return Response.status(Response.Status.OK).entity("Student is Accepted").build();
		}
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Student is not Saved").build();
	};
}
