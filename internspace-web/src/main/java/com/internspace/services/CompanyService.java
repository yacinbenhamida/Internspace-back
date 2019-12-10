package com.internspace.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
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

// To consume other Web Services
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import com.internspace.ejb.abstraction.CompanyEJBLocal;
import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.fyp.StudentFYPSubject.ApplianceStatus;
import com.internspace.entities.users.Company;
import com.internspace.rest.utilities.filters.Secured;

@Path("company")
public class CompanyService {

	@Inject
	CompanyEJBLocal service;

	@Inject
	FYPSheetEJBLocal service_fypFile;

	// Company Section

	@POST
	@Secured
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComapny(Company company) {
		String companyName = company.getName();
		if (companyName.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid company name...").build();

		List<Company> companies = service.findCompaniesByName(companyName, 1, false);

		if (companies != null && companies.size() > 0)
			return Response.status(Status.BAD_REQUEST).entity("Company with such name already exists: " + companyName)
					.build();

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
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCompany(Company updateCompany) {
		if (updateCompany.getId() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid company ID...").build();

		Company company = service.findCompany(updateCompany.getId());

		if (company == null)
			return Response.status(Status.BAD_REQUEST)
					.entity("Failed to find the company to update. Please provide a valid company ID...").build();

		service.updateCompany(updateCompany);

		return Response.status(Response.Status.OK)
				.entity("Successfully UPDATED Company for ID: " + updateCompany.getId()).build();
	}

	@DELETE
	@Path("/delete")
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCompany(@QueryParam(value = "company") long companyId) {
		if (companyId == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		Company company = service.findCompany(companyId);

		if (company == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Can't find Company to delete with ID: " + companyId).build();

		service.deleteCompany(company);
		return Response.status(Response.Status.OK).entity("Successfully DELETED Company for ID: " + companyId).build();
	}

	@DELETE
	@Path("/delete/name")
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCompanyByName(@QueryParam(value = "company") String companyName) {
		if (companyName.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid company name...").build();

		List<Company> companies = service.findCompaniesByName(companyName, 1, false);

		if (companies == null || companies.size() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Company with such name doens't exist: " + companyName)
					.build();

		Company company = companies.get(0);

		service.deleteCompanyById(company.getId());

		return Response.status(Response.Status.OK)
				.entity("Successfully DELETED Company by name for ID: " + company.getId()).build();
	}

	// Subjects Section

	@POST
	@Path("/subjects/add")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSubject(@QueryParam(value = "title") String title, @QueryParam(value = "content") String content,
			@QueryParam(value = "max_applicants") int maxApplicants, @QueryParam(value = "company") long companyId,
			@QueryParam(value = "fyp_file") long fypFileId) {
		// Defaulted to null in case this subject
		// Is inserted with conjunction to a fyp_file
		Company company = null;
		FYPFile fypFile = null;

		// Find relevant company if any
		if (companyId > 0) // Valid input
		{
			company = service.findCompany(companyId);

			if (company == null)
				System.out.println("Failed to load company for ID: " + companyId);
		}

		if (fypFileId > 0) // Valid input
		{
			fypFile = service_fypFile.getFYFileById(fypFileId);

			if (fypFile == null)
				System.out.println("Failed to load fypFile for ID: " + fypFileId);
		}

		FYPSubject subject = new FYPSubject(company, fypFile, title, content, maxApplicants);
		service.createSubject(subject);

		return Response.status(Response.Status.OK).entity("Successfully Inserted a new Subject.").build();
	}

	@POST
	@Path("/subjects/addobj")
	// @Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSubjectObject(FYPSubject newSubject) {
		// Defaulted to null in case this subject
		// Is inserted with conjunction to a fyp_file
		Company company = null;
		FYPFile fypFile = null;

		// Find relevant company if any
		if (newSubject.getCompany().getId() <= 0) // Valid input
		{
			if (company == null)
				System.out.println("Please provide a valid company id, got: " + newSubject.getCompany().getId());
		}

		service.createSubject(newSubject);

		return Response.status(Response.Status.OK).build();
	}
	
	@GET
	@Path("/subjects/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSubjects() {
		return Response.ok(service.getAllSubjects()).status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600").build();
	}

	@GET
	@Path("/subjects/find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findSubject(@QueryParam("subject") long subjectId) {

		FYPSubject subject = service.findSubject(subjectId);

		if (subject == null)
			return Response.status(Status.BAD_REQUEST)
					.entity("Failed to find the Subject to update. Please provide a valid Subject ID...").build();

		return Response.status(Status.BAD_REQUEST).entity(subject).build();
	}

	@GET
	@Path("/subjects/allbycompany")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFypSubjectsByCompany(@QueryParam("company") long companyId,
			@QueryParam("filter-untaken") boolean filteruntaken) {
		List<FYPSubject> subjects;

		if (companyId > 0)
			subjects = service.getFypSubjectsByCompany(companyId, filteruntaken);
		else
			subjects = service.getAllSubjects();

		return Response.ok(subjects).status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600").build();
	}

	@PUT
	@Path("/subjects/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSubject(FYPSubject updateSubject) {
		if (updateSubject.getId() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid Subject ID...").build();

		FYPSubject subject = service.findSubject(updateSubject.getId());

		if (subject == null)
			return Response.status(Status.BAD_REQUEST)
					.entity("Failed to find the Subject to update. Please provide a valid Subject ID...").build();

		subject.setTitle(updateSubject.getTitle());
		subject.setContent(updateSubject.getContent());

		// Only change apply max appliacants when new value > cur applicants
		// if(subject.get)

		service.updateSubject(subject);

		return Response.status(Response.Status.OK)
				.entity("Successfully UPDATED Subject for ID: " + updateSubject.getId()).build();
	}

	@DELETE
	@Path("/subjects/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSubject(@QueryParam(value = "subject") long subjectId) {
		if (subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		FYPSubject subject = service.findSubject(subjectId);

		if (subject == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Can't find suject to delete with ID: " + subjectId).build();

		boolean success = service.deleteSubject(subject);
		
		if(!success)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Won't delete this subject, it is already linked to a fyp file... | subjectId: " + subjectId).build();

		
		return Response.status(Response.Status.OK).entity("Successfully DELETED Subject for ID: " + subjectId).build();
	}

	// Advanced

	@GET
	@Path("/subjects/sfs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentToSubject(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "subject") long subjectId) {
		StudentFYPSubject SFS = service.getStudentToSubject(studentId, subjectId);

		if (SFS == null)
			return Response.status(Response.Status.BAD_REQUEST).entity("No match.").build();

		return Response.status(Response.Status.OK).entity(SFS).build();
	}

	@GET
	@Path("/subjects/sfs/bysubject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentFypSubjectsOfSubjectByStatus(@QueryParam(value = "subject") long subjectId,
			@QueryParam(value = "status") ApplianceStatus status, @QueryParam(value = "fetch-all") boolean fetchAll) {
		if (subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST).entity("Check ID inputs, got (" + subjectId + ")")
					.build();

		List<StudentFYPSubject> SFSs = service.getStudentFypSubjectsOfSubjectByStatus(subjectId, status, fetchAll);

		// if (SFSs == null || SFSs.size() == 0)
			// return Response.status(Response.Status.OK).entity("No matching").build();

		return Response.status(Response.Status.OK).entity(SFSs).build();
	}

	@GET
	@Path("subjects/sfs/bystudent")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentFypSubjectsOfStudentByStatus(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "status") ApplianceStatus status, @QueryParam(value = "fetch-all") boolean fetchAll) {
		if (studentId == 0)
			return Response.status(Response.Status.BAD_REQUEST).entity("Check ID inputs, got (" + studentId + ")")
					.build();

		List<StudentFYPSubject> SFSs = service.getStudentFypSubjectsOfStudentByStatus(studentId, status, fetchAll);

		if (SFSs == null || SFSs.size() == 0)
			return Response.status(Response.Status.BAD_REQUEST).entity("No matching").build();

		return Response.status(Response.Status.OK).entity(SFSs).build();
	}

	/***
	 * Returns ordered list of suggested subjects ids.
	 * @param studentId: Student to suggest subjects
	 * @param filterUntaken: Whether we should filter non-taken subjects out.
	 * @return String, will need to parse this output...
	 */
	@GET
	@Path("/subjects/suggestion/student")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSuggestedSubjectsByStudent(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "filter-untaken") boolean filterUntaken) {
		
		if (studentId == 0)
			return Response.status(Response.Status.BAD_REQUEST).entity("Check ID inputs, got (" + studentId + ")")
					.build();

		// Call service here.
		// Return a heatmap matrix of indices to categories
		
        try {
        	Client client = ClientBuilder.newClient();
        	
        	WebTarget webTarget 
        	  = client.target("http://127.0.0.1:5000");

        	WebTarget subjectSuggestionWebTarget 
        	  = webTarget.path("/" + studentId);
        	
        	Invocation.Builder invocationBuilder 
        	  = subjectSuggestionWebTarget.request(MediaType.APPLICATION_JSON);
        	
        	Response response 
        	  = invocationBuilder
        	  .get();
        	//(Entity.entity(subjects, MediaType.APPLICATION_JSON));
        	
        	String responseStr = response.readEntity(String.class);
        	System.out.println(responseStr);
        	JsonReader jsonReader = Json.createReader(new StringReader(responseStr));
        	JsonArray subjectsIds = jsonReader.readArray();
        	
        	List<FYPSubject> subjects = new ArrayList<FYPSubject>();
        	
        	// Get subjects now...
            for (int i = 0; i < subjectsIds.size(); i++) {
               Long id = Long.parseLong(subjectsIds.get(i).toString());
               FYPSubject subject = service.findSubject(id);
               
               if(subject != null)
            	   subjects.add(subject);
            }
        	
        	return Response.ok(subjects).build();
        	
        } catch (Exception e) {
 
            e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity("Server failed, please check that Python service is up and running...")
					.build();
		
        }
		
	}

	@GET
	@Path("/subjects/apply")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentApplyToSubject(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "subject") long subjectId) {
		if (studentId == 0 || subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Check ID inputs, got (" + studentId + "," + subjectId + ")").build();

		boolean success = service.tryApplyOnSubject(studentId, subjectId);

		String outputMsg = "Successfully applied.";
		
		if (!success)
			outputMsg = "Failed to apply, you might be already applied, accepted or rejected. also check if given student and subject ids are valid...";

		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("/subjects/unapply")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentUnapplyToSubject(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "subject") long subjectId) {
		if (studentId == 0 || subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Check ID inputs, got (" + studentId + "," + subjectId + ")").build();

		boolean success = service.tryUnapplyOnSubject(studentId, subjectId);

		String outputMsg = "Successfully unapplied.";
		if (!success)
			outputMsg = "Failed to unapply, you might be already unapplied, accepted or rejected. also check if given student and subject ids are valid... ";

		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Path("/subjects/toggle_appliance")
	@Produces(MediaType.APPLICATION_JSON)
	public Response studentToggleApplianceToSubject(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "subject") long subjectId) {
		if (studentId == 0 || subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Check ID inputs, got (" + studentId + "," + subjectId + ")").build();

		boolean success = service.studentToggleAppliance(studentId, subjectId);

		String outputMsg = "Successfully unapplied.";
		if (!success)
			outputMsg = "Failed to unapply, you might be already unapplied, accepted or rejected ";

		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();
	}

	@GET
	@Path("/subjects/accept")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptStudentAppliance(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "subject") long subjectId) {
		if (studentId == 0 || subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Check ID inputs, got (" + studentId + "," + subjectId + ")").build();

		boolean success = service.acceptStudentAppliance(studentId, subjectId);

		String outputMsg = "Successfully accepted students' appliance.";

		if (!success)
			outputMsg = "Failed to accept, it might be already none, rejected or the appliance count is maximal.";

		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();
	}

	@GET
	@Path("/subjects/refuse")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refuseStudentAppliance(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "subject") long subjectId, @QueryParam(value = "reason") String reason) {
		if (studentId == 0 || subjectId == 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Check ID inputs, got (" + studentId + "," + subjectId + ")").build();

		// Appropriate refusal text
		reason = (reason == null || reason.isEmpty()) ? StudentFYPSubject.defaultReason : reason;

		boolean success = service.refuseStudentAppliance(studentId, subjectId, reason);

		String outputMsg = "Successfully refused student's appliance.";

		if (!success)
			outputMsg = "Failed to accept, it might be already none, accepted or matching doesn't exist.";

		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();

	}
	
	// Subscription
	
	@POST
	@Path("/company/subscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribe(Company company)
	{
		if (company == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Please, provide a valid company").build();

		List<Company> foundCompanies = service.findCompaniesByName(company.getName(), 1, false); 
		
		if(foundCompanies != null && foundCompanies.size() > 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("A similar company already exists").build();
			
		// Explicitly set it to be not real
		company.setIsReal(false);
		service.createCompany(company);
		
		String outputMsg = "";
		boolean success = true;
		return Response.status(success ? Response.Status.OK : Response.Status.BAD_REQUEST).entity(outputMsg).build();
	}

	
	@POST
	@Path("/company/set_approval")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response approveCompany(
			@QueryParam(value = "name") String companyName,
			@QueryParam(value = "approve") boolean toApproved)
	{
		if(companyName == null || companyName.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Please provide a valid name.").build();
			
		List<Company> foundCompanies = service.findCompaniesByName(companyName, 1, false); 
		
		if(foundCompanies == null || foundCompanies.size() == 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Company with such name doesn't exist: " + companyName).build();
			
		Company company = foundCompanies.get(0);
		
		if(company.getIsReal().equals(toApproved))
			if(foundCompanies == null || foundCompanies.size() == 0)
				return Response.status(Response.Status.OK)
						.entity("Failed, Company " + companyName + " is already " + (toApproved ? "approved" : "not approved")).build();
		
		company.setIsReal(toApproved);
		service.updateCompany(company);		
		
		return Response.status(Response.Status.OK)
				.entity("Company " + companyName + " is now " + (toApproved ? "approved" : "not approved")).build();

	}
}
