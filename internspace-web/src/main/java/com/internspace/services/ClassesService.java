package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.ejb.abstraction.StudyClassesEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.university.ClassOption;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.university.UniversitaryYear;

@Path("classes")
public class ClassesService {
	@Inject
	StudyClassesEJBLocal service;
	
	@PUT
	@Path("add/{yearId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(StudyClass c,@PathParam("yearId") int endYear) {
		StudyClass res = service.addClass(c, endYear);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@GET
	@Path("{depId}/{endYear}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClassesOfDept(@PathParam("depId") long depId,@PathParam("endYear") int endYear) {
		List<StudyClass> res = service.getClassesOfDepartment(depId, endYear);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	@GET
	@Path("options/{depId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClassOptionsOfDept(@PathParam("depId") long depId) {
		List<ClassOption> res = service.getAllClassOptionsOfDept(depId);
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	@GET
	@Path("uniyears")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUniYears() {
		List<UniversitaryYear> res = service.getRegisteredUniYears();
		if(res!=null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
}
