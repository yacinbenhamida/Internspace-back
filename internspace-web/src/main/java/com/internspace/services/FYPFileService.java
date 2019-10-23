package com.internspace.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.FYPFileEJBLocal;
import com.internspace.entities.fyp.FYPFile;



@Path("file")
@Stateless
public class FYPFileService {

	
	@Inject
	FYPFileEJBLocal fileService;
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK)
				.entity(fileService.getAll()).build();
		
	
		
	}
	
	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response  addFile(FYPFile file) {
		
		fileService.addFYPFile(file);
		return Response.status(Status.OK).entity(file).build();
	}
}
