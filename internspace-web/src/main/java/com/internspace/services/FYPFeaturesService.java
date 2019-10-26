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

import com.internspace.ejb.FYPFeaturesEJB;
import com.internspace.ejb.abstraction.FYPFeaturesEJBLocal;
import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;


@Path("features")
@Stateless
public class FYPFeaturesService {
	


	@Inject
	FYPFeaturesEJBLocal service;

	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK)
				.entity(service.ListFYPFeature()).build();
		
	
		
	}
	
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response  addFile(FYPFeature feature ) {
		
		service.addFYPFeature(feature);
		return Response.status(Status.OK).entity(feature).build() ;
		
		
	}
	
}
