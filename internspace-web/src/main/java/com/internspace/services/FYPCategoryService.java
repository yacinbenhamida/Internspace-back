package com.internspace.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategories(@PathParam("id") long id ) {
		List<FYPCategory> res = service.getAllCategories(id);
		if(res.size()>0) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NO_CONTENT).entity("no records").build();
	}
	
	@GET
	@Path("suggestions/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSuggestions(@PathParam("id") long id ) {
		List<FYPCategory> res = service.getSuggestedCategories(id);
		if(res.size()>0) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.NO_CONTENT).entity("no records").build();
	}
	
	@POST
	@Path("edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editCat(FYPCategory f) {
		FYPCategory res = service.editCategory(f);
		if(res != null) {
			return Response.status(Response.Status.OK).entity(res).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("no records").build();
	}
	
}
