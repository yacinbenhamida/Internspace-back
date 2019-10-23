package com.internspace.services;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.DepartementEJBLocal;

@Path("department")
public class DepartementService {

	@Inject
	DepartementEJBLocal depatementServices;
	
	@PUT
	@Path("departmentDirector/{idDepartment}/{idEmployee}")
	public Response setDepartmentDirector(@PathParam("idDepartment") long idDepartment, @PathParam("idEmployee") long idEmployee) {
		int result = depatementServices.setDepartmentDirector(idDepartment, idEmployee);
		if(result == 1) {
			return Response.status(Response.Status.OK).entity("Sucess").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
}
