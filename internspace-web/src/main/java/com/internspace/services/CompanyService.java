package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.internspace.ejb.FYPSheetEJB;
import com.internspace.ejb.abstraction.CompanyEJBLocal;
import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.users.Company;

@Path("company")
public class CompanyService {

	@Inject
	CompanyEJBLocal service;

	@Inject
	FYPSheetEJBLocal service_fypFile;
	
	// Company Section
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComapny(Company company) 
	{
		String companyName = company.getName();
		if(companyName.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid company name...").build();
		
		List<Company> companies = service.findCompaniesByName(companyName, 1, false);
		
		if(companies != null && companies.size() > 0)
			return Response.status(Status.BAD_REQUEST).entity("Company with such name already exists: " + companyName).build();
			
		service.createCompany(company);
		
		return Response.status(Status.OK).entity(company).build();
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK).entity(service.getAll()).build();
	}
	
	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCompany(Company updateCompany)
	{
		if(updateCompany.getId() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid company ID...").build();
		
		Company company = service.findCompany(updateCompany.getId());
		
		if(company == null)
			return Response.status(Status.BAD_REQUEST).entity("Failed to find the company to update. Please provide a valid company ID...").build();
		
		service.updateCompany(updateCompany);
	    
		return Response.status(Response.Status.OK).entity("Successfully UPDATED Company for ID: " + updateCompany.getId()).build();
	}
	
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCompany(
			@QueryParam(value="company")long companyId
			)
	{
		if(companyId == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		Company company = service.findCompany(companyId);
		
		if(company == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Can't find Company to delete with ID: " + companyId).build();
		
		service.deleteCompany(company);
		return Response.status(Response.Status.OK).entity("Successfully DELETED Company for ID: " + companyId).build();
	}
	
	@DELETE
	@Path("/delete/name")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCompanyByName(
			@QueryParam(value="company")String companyName
			)
	{
		if(companyName.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid company name...").build();
		
		List<Company> companies = service.findCompaniesByName(companyName, 1, false);
		
		if(companies == null || companies.size() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Company with such name doens't exist: " + companyName).build();
			
		Company company = companies.get(0);
		
		service.deleteCompanyById(company.getId()); 
		
		return Response.status(Response.Status.OK).entity("Successfully DELETED Company by name for ID: " + company.getId()).build();
	}
	
	// Subjects Section

	@POST
	@Path("/subjects/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSubject(
			@QueryParam(value="title")String title
			,@QueryParam(value="content")String content
			,@QueryParam(value="max_applicants")int maxApplicants
			,@QueryParam(value="company")long companyId
			,@QueryParam(value="fyp_file")long fypFileId
			) 
	{
		// Defaulted to null in case this subject
		// Is inserted with conjunction to a fyp_file
		Company company = null; 
		FYPFile fypFile = null;
		
		// Find relevant company if any
		if(companyId > 0) // Valid input
		{
			company = service.findCompany(companyId);
			
			if(company == null)
				System.out.println("Failed to load company for ID: " + companyId);
		}
		
		if(fypFileId > 0) // Valid input
		{
			fypFile = service_fypFile.getFYFileById(fypFileId);
			
			if(fypFile == null)
				System.out.println("Failed to load fypFile for ID: " + fypFileId);
		}
		
		FYPSubject subject = new FYPSubject(company, fypFile, title, content, maxApplicants);
		service.createSubject(subject);
		
		return Response.status(Response.Status.OK).entity("Successfully Inserted a new Subject.").build();
	}
	
	@GET
	@Path("/subjects/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSubjects() {
		return Response.ok(service.getAllSubjects()).status(200)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600")
				.build();
	}
	
	@GET
	@Path("/subjects/allbycompany")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFypSubjectsByCompany(
			@QueryParam("company") long companyId,
			@QueryParam("filter-untaken") boolean filteruntaken) {
		List<FYPSubject> subjects;
		
		if(companyId > 0)
			subjects = service.getFypSubjectsByCompany(companyId, filteruntaken);
		else
			subjects = service.getAllSubjects();
		
		return Response.ok(subjects).status(200)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600")
				.build();
	}
	
	@PUT
	@Path("/subjects/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSubject(FYPSubject updateSubject)
	{
		if(updateSubject.getId() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid Subject ID...").build();
		
		FYPSubject subject = service.findSubject(updateSubject.getId());
		
		if(subject == null)
			return Response.status(Status.BAD_REQUEST).entity("Failed to find the Subject to update. Please provide a valid Subject ID...").build();
		
		subject.setTitle(updateSubject.getTitle());
		subject.setContent(updateSubject.getContent());
		
		// Only change apply max appliacants when new value > cur applicants
		//if(subject.get)
		
		service.updateSubject(subject);
	    
		return Response.status(Response.Status.OK).entity("Successfully UPDATED Subject for ID: " + updateSubject.getId()).build();
	}
	
	@DELETE
	@Path("/subjects/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSubject(
			@QueryParam(value="subject")long subjectId
			)
	{
		if(subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		FYPSubject subject = service.findSubject(subjectId);
		
		if(subject == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Can't find suject to delete with ID: " + subjectId).build();
		
		service.deleteSubject(subject);
		return Response.status(Response.Status.OK).entity("Successfully DELETED Subject for ID: " + subjectId).build();
	}
	
	// Advanced
	
	@GET
	@Path("/subjects/apply")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentApplyToSubject(
			@QueryParam(value="student")long studentId
			,@QueryParam(value="subject")long subjectId
			)
	{
		boolean success = service.tryApplyOnSubject(subjectId, studentId);
		
		String outputMsg = "Succussfully applied.";
		if(!success)
			outputMsg = "Failed to apply, you might be already applied, accepted or rejected ";
		
		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();
	}
	
	@GET
	@Path("/subjects/unapply")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentUnapplyToSubject(
			@QueryParam(value="student")long studentId
			,@QueryParam(value="subject")long subjectId
			)
	{
		boolean success = service.tryUnapplyOnSubject(subjectId, studentId);
		
		String outputMsg = "Succussfully unapplied.";
		if(!success)
			outputMsg = "Failed to unapply, you might be already unapplied, accepted or rejected ";
		
		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();
	}
	
	@GET
	@Path("/subjects/toggle_appliance")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentToggleApplianceToSubject(
			@QueryParam(value="student")long studentId
			,@QueryParam(value="subject")long subjectId
			)
	{
		boolean success = service.studentToggleAppliance(studentId, subjectId);
		
		String outputMsg = "Succussfully unapplied.";
		if(!success)
			outputMsg = "Failed to unapply, you might be already unapplied, accepted or rejected ";
		
		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();
	}
	
}
