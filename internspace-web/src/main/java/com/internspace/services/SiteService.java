package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.SiteEJBLocal;
import com.internspace.entities.university.Site;

@Path("site")
public class SiteService {
	@Inject
	SiteEJBLocal siteServices;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSite(Site site) {
		int result = siteServices.addSite(site);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("Successfully added Site:\n"+site.toString()).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSite(Site site) {
		int result = siteServices.updateSite(site);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("Successfully updated Site:\n"+site.toString()).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteSite(@PathParam(value="id") long id) {
		int result = siteServices.deleteSite(id);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("Successfully removed Site.\n").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).entity("Could not delete id : "+id).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSites() {
		return Response.status(Response.Status.OK)
				.entity(siteServices.getAllSites()).build();
		
	}
}
