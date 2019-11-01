package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.FYPFileModificationEJBLocal;


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
}
