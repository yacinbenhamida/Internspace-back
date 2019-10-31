package com.internspace.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.CompanyCoordinatesEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileArchive;
import com.internspace.entities.university.CompanyCoordinates;


@Path("CompanyCoord")
@Stateless
public class CompanyCoordiantesService {

	@Inject
	CompanyCoordinatesEJBLocal service;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	public Response  displayList() {
		return Response.status(Status.OK).entity(service.listeCompanyCoordinates()).build();
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("add")
	public Response add(CompanyCoordinates c){
		service.addCompanyCoordinates(c);
		return Response.status(Response.Status.OK).entity(c).build();

	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("delete/{id}")
	public Response deleteArchive(long id){
		service.deleteCompanyCoordinates(id);
		return Response.status(Response.Status.OK).entity("removed").build();

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update")
	public Response updateArchive(CompanyCoordinates c){
		service.updateCompanyCoordinates(c);
		return Response.status(Response.Status.OK).entity(c).build();

	}
}
