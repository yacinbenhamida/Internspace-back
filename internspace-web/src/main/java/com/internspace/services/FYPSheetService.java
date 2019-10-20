package com.internspace.services;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.FYPSheetEJB;
import com.internspace.entities.fyp.FYPFile;

@Path("fypsheet")
public class FYPSheetService {
	@Inject
	FYPSheetEJB fypSheetService;
	
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSheets() {
			return Response.status(Response.Status.OK).entity(fypSheetService.getAllSheets()).build();
	}
}
