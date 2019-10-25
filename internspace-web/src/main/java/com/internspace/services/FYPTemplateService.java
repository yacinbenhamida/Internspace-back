package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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

	@GET
	@Path("/update/element")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateElement(
			@QueryParam(value="id")long id,
			@QueryParam(value="x")float x,
			@QueryParam(value="y")float y,
			@QueryParam(value="h")float h,
			@QueryParam(value="w")float w
			)
	{
		FileTemplateElement templateElement;
		templateElement = new FileTemplateElement(id, x, y, h, w);
		boolean done = service.updateElement(templateElement);
		
		// 422 => Unprocessable Entity
		Status status = done ? Response.Status.ACCEPTED : Response.Status.EXPECTATION_FAILED;
        return Response
        		.status(status)
        		.build();
	}

	@GET
	@Path("/find/name")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findTemplateByName(
			@QueryParam(value="name")String name,
			@QueryParam(value="n")int n,
			@QueryParam(value="like")boolean useLike)
	{
		List<FileTemplate> fypTemplate = service.findTemplatesByName(name, n, useLike);
        return Response.ok(fypTemplate).status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Max-Age", "1209600")
                .build();
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
            return Response.ok(fypTemplates).status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
	}

}
