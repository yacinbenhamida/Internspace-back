package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
// import javax.ws.rs.POST;

import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
//import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.FileTemplateElement;

@Path("template")
@Stateless
public class FYPTemplateService {

	
	@Inject
	FileTemplateEJBLocal service;
	
	/***
	 * Creates a file template and generates adequate elements 
	 * that are relevant to the template's type.
	 * @param templateName
	 */
	@Path("create")
	@Consumes(MediaType.TEXT_PLAIN)
	public void createTemplate(
			@QueryParam(value="TemplateName")String templateName,
			@QueryParam(value="is_fyp")boolean isFyp)
	{
		System.out.print("IsFyp: " + isFyp);
		
		FileTemplate fileTemplate = new FileTemplate(templateName, isFyp);
		fileTemplate.setTemplateName(templateName);
		
		service.createTemplate(fileTemplate);
	}

	public void createElement(FileTemplateElement element) {
		service.createElement(element);

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
		List<FileTemplate> fypTemplates = service.getAllTemplates();
        if (!fypTemplates.isEmpty()) {
        	// TODO: UGLY, have to secure this...
        	
        	/*
        	for(FileTemplate template : fypTemplates)
        	{
        		for(FileTemplateElement element: template.getFyptElements())
        		{
        			// For cases where we create a template and select it right away...
        			element.setFypTemplate(null);			
        		}       			
        	}
        	*/
        	
            return Response.ok(fypTemplates).status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    //.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    //.header("Access-Control-Allow-Credentials", "true")
                    //.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
	}

}
