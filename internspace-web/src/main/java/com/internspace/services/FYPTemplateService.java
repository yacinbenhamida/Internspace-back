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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.fyp.FYPFile;
//import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.FileTemplateElement;
import com.internspace.entities.users.Employee;

@Path("template")
@Stateless
public class FYPTemplateService {

	@Inject
	FileTemplateEJBLocal service;
	@Inject
	InternshipDirectorEJBLocal service_InternshipDirector;
	
	/***
	 * Creates a file template and generates adequate elements 
	 * that are relevant to the template's type.
	 * @param templateName
	 */
	//@POST
	@GET
	@Path("create")
	@Produces(MediaType.TEXT_PLAIN)
	public Response createTemplate(
			@QueryParam(value="name")String templateName,
			@QueryParam(value="is_fyp")boolean isFyp,
			@QueryParam(value="editor")long internshipDirectorId
			)
	{
		Employee internshipDirector = service_InternshipDirector.getInternshipDirectorById(internshipDirectorId);
		
		if(internshipDirector == null)
		{
			return Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity("Failed to find an Internship Director for ID: " + internshipDirectorId).build();
		}
		
		System.out.println("Creating tamplate");
		
		// To check if it exists
		List<FileTemplate> fileTemplates = service.findTemplatesByName(templateName, 1, false);
		
		// Change desired name with number concatenation
		if(fileTemplates != null && fileTemplates.size() > 0)
		{
			System.out.println("Checking for max DIGIT");
			
			Pattern p = Pattern.compile("\\d+");

	        // If an occurrence if a pattern was found in a given string...
	        Long lastDigits = null;
	        int startIndex = -1;
	        
			fileTemplates = service.findTemplateVersionsByName(templateName);
				
	        for(FileTemplate e : fileTemplates)
	        {	        
				System.out.println("Checking with file template: " + e.getTemplateName());
				
    	        Matcher m = p.matcher(e.getTemplateName());
	        	int howManyFound = 0;
        		Long val = null;
        		int foundIdx = -1;
        		  	
    	        while(m.find()) 
	        	{
    	        	val = Long.valueOf(m.group());
    	        	foundIdx = m.start();
    				System.out.println("VAL0: " + val);
    					
    	        	howManyFound += 1;
    	        	
    	        	if(howManyFound > 1)
    	        		break;
    	        	
		        	System.out.println(startIndex + " : " + m.group());
		        }
    	        
	        	if(howManyFound == 1 && (lastDigits == null || lastDigits < val))
	        	{
	        		startIndex = foundIdx;
		        	lastDigits = val;
	        	}	
	
		        if(startIndex != -1)
		        	templateName = templateName.substring(0, startIndex) + (lastDigits + 1);
		        else
		        	templateName = templateName + 1;
	        }
	        
	        System.out.println("---");

	        System.out.println(lastDigits);
	        System.out.println(templateName);
		}
		
		FileTemplate fileTemplate;
		fileTemplate = new FileTemplate(templateName, isFyp, internshipDirector);
		fileTemplate.setTemplateName(templateName);
		
		service.createTemplate(fileTemplate);
		return Response.status(Response.Status.OK).entity("Successfully Inserted a new Template.").build();
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
	
	@GET
	@Path("/editor/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllByEditor(@QueryParam(value="editorId")long editorId)
	{
		List<FileTemplate> fypTemplates = service.getAllByEditor(editorId);
		
        return Response.status(Response.Status.OK).entity(fypTemplates).build();
	}
	
	@GET
	@Path("/find-file/name")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findFypFileByName(
			@QueryParam(value="name")String name,
			@QueryParam(value="n")int n,
			@QueryParam(value="like")boolean useLike)
	{
		List<FYPFile> fypFile = service.findFypFileByName(name, n, useLike);
        return Response.ok(fypFile).status(200)
                .build();
	}


}
