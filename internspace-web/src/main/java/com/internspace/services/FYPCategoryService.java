package com.internspace.services;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.FYPCategoryEJBLocal;
import com.internspace.entities.fyp.FYPCategory;

@Path("category")
public class FYPCategoryService {
	@EJB
	FYPCategoryEJBLocal service;
	
	@GET
	@Path("approve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response approveSuggestion(@PathParam("id") long id ) {
		FYPCategory target = service.approveCategory(id);
		if(target !=null) {
			return Response.status(Response.Status.OK).entity(target).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("unregistered").build();
	}
	
	@GET
	@Path("refuse/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refuseSuggestion(@PathParam("id") long id ) {
		if(service.deleteCategory(id)) {
			return Response.status(Response.Status.OK).entity("deleted").build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("unregistered").build();
	}
}
