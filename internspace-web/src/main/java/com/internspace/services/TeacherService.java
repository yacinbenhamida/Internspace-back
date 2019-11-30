package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.internspace.ejb.abstraction.TeacherEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.university.CompanyCoordinates;

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
	@GET
	@Path("/supervised/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetSupervisedFyp(@PathParam (value="id") long id)
	{
		List<FYPFile> fypfiles=service.getSupervisedFYPfiles(id);
		 return Response.ok(fypfiles).status(200)
			        .header("Access-Control-Allow-Origin", "*")
			        .header("Access-Control-Max-Age", "1209600")
			        .build();
				
}
	@GET
	@Path("/pr/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetProFyp(@PathParam (value="id") long id)
	{
		List<FYPFile> fypfiles=service.getprotractoredFYPfiles(id);
		 return Response.ok(fypfiles).status(200)
			        .header("Access-Control-Allow-Origin", "*")
			        .header("Access-Control-Max-Age", "1209600")
			        .build();
				
}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response addCategory(
			@QueryParam(value="name")String Name)
			
	{
		FYPCategory category = new FYPCategory();
		category.setName(Name);
		
		service.ProposeFYPCategory(category);
		return Response.status(Status.OK).entity(category).build();
	}
	
	@PUT
	@Path("/edit/{id}/{id2}")
	
	public void editFYPSheet(@PathParam(value="id")long id,@PathParam(value="id2")long id2) {
		service.ValidateMajorModification(id,id2);
		
		
	}
	@GET
	@Path("/pre_valid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetprevalidatedFypFile(@PathParam (value="id") long id)
	{
		List<FYPFile> fypfiles=service.getPrevalidatedFiles(id);
		 return Response.ok(fypfiles).status(200)
			        .header("Access-Control-Allow-Origin", "*")
			        .header("Access-Control-Max-Age", "1209600")
			        .build();
				
}
	@GET
	@Path("/pending")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetPendingFypFile()
	{
		List<FYPFile> fypfiles=service.getPendingFYPFiles();
		 return Response.ok(fypfiles).status(200)
			        .header("Access-Control-Allow-Origin", "*")
			        .header("Access-Control-Max-Age", "1209600")
			        .build();
				
}
	

	@GET
	@Path("/allCat")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getallcategories()
	{
		List<FYPCategory> fypfiles=service.getAllCategories();
		 return Response.ok(fypfiles).status(200)
			        .header("Access-Control-Allow-Origin", "*")
			        .header("Access-Control-Max-Age", "1209600")
			        .build();
				
}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/prevalidate/{id}")
	public Response updateArchive(@PathParam (value="id") long file ){
		FYPFile f=service.PrevalidateFYPFile(file);
		return Response.status(Response.Status.OK).entity("done").build();

	}
	

}
