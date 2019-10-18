package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.users.Student;

@Path("internship")
@Stateless
public class InternshipDirectorServices {
	
	
	@Inject
	InternshipDirectorEJBLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listLate")
	public List<Student> getLastStudent(@QueryParam(value = "year") int year){
		return service.getLateStudentsList(year);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listmailing")
	public void sendMail(@QueryParam(value = "year") int year){
		 service.sendMail(year);
	};

}
