package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.internspace.ejb.abstraction.FYPSheetHistoryEJBLocal;
import com.internspace.entities.fyp.FYPSheetHistory;

@Path("FYPSheetHistory")
public class FYPSheetHistoryService {
	@Inject
	FYPSheetHistoryEJBLocal service;
	
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFYPSHistory(FYPSheetHistory history) {
		long id = -1;
		id = service.recordOperation(history);
		if(id!=-1) {
			history = service.getById(id);
			return Response.status(Response.Status.OK).entity(history).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@GET
	@javax.ws.rs.Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Response.Status.OK).entity(service.getAllRecordedOperations()).build();
	}
	@DELETE
	@Path("delete/{id}")
	public Response removeOperation(@PathParam(value="id") long id) {
		FYPSheetHistory target = service.getById(id);
		if(target != null) {
			service.removeOperation(id);
			return Response.status(Response.Status.OK).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
}
