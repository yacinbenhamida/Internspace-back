package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
// import javax.ws.rs.POST;

import com.internspace.ejb.abstraction.FYPTemplateEJBLocal;
import com.internspace.entities.FYPTElement;
import com.internspace.entities.FYPTemplate;

@Path("template")
@Stateless
public class FYPTemplateService {

	@Inject
	FYPTemplateEJBLocal service;
	
	@Path("create/{TemplateName}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void createNewTemplate(@PathParam(value="TemplateName")String fypTemplateName) {
		FYPTemplate newFypTemplate = new FYPTemplate();
		newFypTemplate.setTemplateName(fypTemplateName);
		
		System.out.println("Adding: " + newFypTemplate);
		
		service.createNewTemplate(newFypTemplate);
	}

	public void createNewElement(FYPTElement newFyptElement, FYPTemplate toTemplate) {
		service.createNewElement(newFyptElement, toTemplate);
	}

	@Path("debug")
	@Produces(MediaType.TEXT_PLAIN)
	public String debugString()
	{
		return "Rest is working!";
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<FYPTemplate> fypTemplates = service.getAll();
        if (!fypTemplates.isEmpty()) {
        	// TODO: UGLY, have to secure this...
            return Response.ok(fypTemplates).status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
	}

}
