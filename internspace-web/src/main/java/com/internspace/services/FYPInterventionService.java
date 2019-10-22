package com.internspace.services;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	@Path("assign/{idt}/{idfile}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignTeacherToFYPSheetWithRole(long idTeacher,long idFYPS,String role) {
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build(); 
	}
	public Response editInterventionActorRole(long fypinterventionId,long idTeacher,String newRole) {
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
		
	}
	public Response getAllTeachersRankedByNumberOfSupervisions() {
		return Response.status(Response.Status.NOT_FOUND).entity("norecords").build();
	}
}
