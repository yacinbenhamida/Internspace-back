package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.TeacherEJBLocal;
import com.internspace.entities.fyp.FYPFile;

@Stateless
@Path("teachers")
public class TeacherService {

	@Inject
	TeacherEJBLocal service;
	
	@GET
	@Path("/pending")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetFYPFILEPending() {
		List<FYPFile> fypfiles = service.getPendingFYPFiles();

        return Response.ok(fypfiles).status(200)
	        .header("Access-Control-Allow-Origin", "*")
	        .header("Access-Control-Max-Age", "1209600")
	        .build();
		
	}
}
