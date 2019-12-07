package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.DepartementEJBLocal;
import com.internspace.entities.university.Departement;

@Path("department")
public class DepartementService {

	@Inject
	DepartementEJBLocal depatementServices;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{siteId}")
	public Response getAllDeptsOfsite(@PathParam("siteId") long siteId) {
		return Response.status(Status.OK).entity(depatementServices.getAllDepartmentsOfSite(siteId)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("add")
	public Response insertDepartment(Departement dep) {
		if(dep!=null) {
			System.out.println("department inserting "+dep);
			long depId = depatementServices.addDepartment(dep);
			return Response.status(Status.OK).entity(depatementServices.getDeptById(depId)).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}
	
}
