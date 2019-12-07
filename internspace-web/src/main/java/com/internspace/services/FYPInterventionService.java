package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.FYPInterventionEJBLocal;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;

@Path("interventions")
public class FYPInterventionService {

	@Inject
	FYPInterventionEJBLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInterventions() {
		if(service.getAll() != null ) return Response.status(Response.Status.OK).entity(service.getAll()).build();
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	@GET
	@Path("ranked/{depId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeachersByNumberOfSupervisions() {		
		if(service.getAllTeachersRankedByNumberOfSupervisions() != null )
			return Response.status(Response.Status.OK).entity(service.getAllTeachersRankedByNumberOfSupervisions()).build();
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	@GET
	@Path("assign/{idt}/{idfile}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignTeacherToFYPSheetWithRole(@PathParam("idt")long idTeacher,@PathParam("idfile")long idFYPS,@PathParam("role")String role) {
		FYPIntervention intervention = service.assignTeacherToFYPSheetWithRole(idTeacher, idFYPS, role);
		System.out.println(role);
		if(intervention!=null) {
			return Response.status(Response.Status.OK).entity(intervention).build(); 
		}
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build(); 
	}
	@GET
	@Path("assignMark/{value}/{intId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignMarkToIntervention(@PathParam("intId")long idIntervention,@PathParam("value")int value) {
		FYPIntervention intervention = service.saveMark(value, idIntervention);
		if(intervention!=null) {
			return Response.status(Response.Status.OK).entity(intervention).build(); 
		}
		return Response.status(Response.Status.NOT_FOUND).entity("no records").build(); 
	}
	@GET
	@Path("edit/{idInt}/{role}/{idTeacher}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editInterventionActorRole(@PathParam("role")String newRole,@PathParam("idInt")long idInt,@PathParam("idTeacher")long idTeacher) {
		FYPIntervention intervention2 = service.editInterventionActorRole(idInt, newRole,idTeacher);
		if(intervention2 != null) {
			return Response.status(Response.Status.OK).entity(intervention2).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	public Response getAllTeachersRankedByNumberOfSupervisions() {
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
	@DELETE
	@Path("delete/{idInt}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteIntervetnion(@PathParam("idInt")long idInt) {
		boolean done = service.deleteIntervention(idInt);
		if(done) {
			return Response.status(Response.Status.OK).entity("deleted").build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("could not find record").build();
	}
	
	
	@GET
	@Path("getInterventions/{idFYPS}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFypInterventionsOfSheet(@PathParam("idFYPS")long idFYPS) {
		List<FYPIntervention> list = service.getInterventionsOfFYPSheet(idFYPS);
		if(list!=null) {
			return Response.status(Response.Status.OK).entity(list).build(); 
		}
		return Response.status(Response.Status.NOT_FOUND).entity("no records").build(); 
	}
	@GET
	@Path("getInterventionsOfTeacher/{idTeacher}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFypInterventionsOfTeacher(@PathParam("idTeacher")long idTeacher) {
		List<FYPIntervention> list = service.getInterventionsOfTeacher(idTeacher);
		if(list!=null) {
			return Response.status(Response.Status.OK).entity(list).build(); 
		}
		return Response.status(Response.Status.NOT_FOUND).entity("no records").build(); 
	}
	
}
