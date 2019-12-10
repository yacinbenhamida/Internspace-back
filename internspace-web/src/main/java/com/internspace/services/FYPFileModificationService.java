package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.FYPFileModificationEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileModification;


@Path("FYPSFileModification")
public class FYPFileModificationService {


	@Inject
	FYPFileModificationEJBLocal service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK)
				.entity(service.getAllFilesModification()).build();
		
	
		
	}
	
	

	@PUT
	@Path("edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editFYPSheet(FYPFile file) {
		FYPFile res = service.editFYPSheet(file);
 
			return Response.status(Response.Status.OK).entity(res).build();
		
		
	}
	

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlll() {
		return Response.status(Status.OK)
				.entity(service.getAll()).build();
		
	
		
	}
	@POST
	@Path("accept")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response accept(@QueryParam(value = "id") long id) {
		return Response.status(Status.OK)
				.entity(service.acceptModification(id)).build();
		
	
		
	}
}
