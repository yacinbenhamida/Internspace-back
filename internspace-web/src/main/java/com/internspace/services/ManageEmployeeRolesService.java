package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.ManageEmployeeRolesEJBLocal;
import com.internspace.entities.university.University;

@Path("manageEmployeeRoles")
public class ManageEmployeeRolesService {

	@Inject
	ManageEmployeeRolesEJBLocal service;
	
	@PUT
	@Path("department/{idDepartment}/employee/{idEmployee}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setDepartmentDirector(@PathParam(value="idDepartment") long idDepartment, @PathParam(value="idEmployee") long idEmployee) {
		int result = service.setDepartmentDirector(idDepartment, idEmployee);
		if(result == 1) {
			return Response.status(Response.Status.OK).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@PUT
	@Path("site/{idSite}/employee/{idEmployee}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response setSiteInternshipDirector(@PathParam(value="idSite") long idSite, @PathParam(value="idEmployee") long idEmployee) {
		int result = service.setSiteInternshipDirector(idSite, idEmployee);
		if(result == 1) {
			return Response.status(Response.Status.OK).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
}
