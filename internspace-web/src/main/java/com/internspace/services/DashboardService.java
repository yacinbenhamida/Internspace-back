package com.internspace.services;

import java.util.List;
import java.util.Map;

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
import javax.ws.rs.POST;

import com.internspace.ejb.abstraction.DashboardEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Student;

@Path("dashboard")
@Stateless
public class DashboardService {

	@Inject
	DashboardEJBLocal service;

	@GET
	@Path("/site/students")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsBySite(@QueryParam("site") long siteId) {
		List<Student> students = service.getStudentsBySite(siteId);

		return Response.ok(students).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();

		// return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/internship/distribution")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsLocationDistribution(@QueryParam("uni") long uniId,
			@QueryParam("abroad") boolean abroad) {
		float distribution = service.getStudentsLocalAbroadDistribution(uniId, abroad);

		return Response.ok(distribution).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();

		// return Response.status(Response.Status.NOT_FOUND).build();
	}

	// getAbroadPercentagePerYearByUY

	@GET
	@Path("/distribution/abroad")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAbroadPercentagePerYear(@QueryParam("uni") long uniId) {
		Map<Long, Float> out = service.getAbroadPercentagePerYear(uniId);

		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();

		// return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/distribution/location")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsDistributionByLocationAndUY(@QueryParam("uni") long uniId,
			@QueryParam("location") String location, @QueryParam("uy") long uyId) {
		float out = service.getStudentsDistributionByLocationAndUY(uniId, location, uyId);

		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();

		// return Response.status(Response.Status.NOT_FOUND).build();
	}

	// getMostCompanyAcceptingInternsWithUniversity

	@GET
	@Path("/distribution/topcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMostCompanyAcceptingInternsWithUniversity(@QueryParam("uni") long uniId,
			@QueryParam("n") int n) {
		List<Company> out = service.getMostCompanyAcceptingInternsWithUniversity(uniId, n);

		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();

		// return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/internship/category")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInternshipsByCategory(@QueryParam("uni") long uniId, @QueryParam("category") long categoryId) {
		List<FYPSubject> out = service.getInternshipsByCategory(uniId, categoryId);

		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();

		// return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/company/category/most-requested")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMostRequestedCategoriesByCompanies() {
		List<FYPCategory> out = service.getMostRequestedCategoriesByCompanies();

		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				// .header("Access-Control-Allow-Headers", "origin, content-type, accept,
				// authorization")
				// .header("Access-Control-Allow-Credentials", "true")
				// .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,
				// HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}
	
	// DONE
	@GET
	@Path("/company/category/evolution")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInternshipEvolutionPerUYByCategory(
			@QueryParam("uni") long uniId,
			@QueryParam("category") long categoryId
			)
	{
		Map<Long, List<FYPSubject>> out = service.getInternshipEvolutionPerUYByCategory(uniId, categoryId);
		
		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600").build();

	}
	
	// USED
	@GET
	@Path("/helper/categories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories() {
		List<FYPCategory> categories = service.getCategories();
		
		return Response.ok(categories).status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600").build();
	}
	
	@GET
	@Path("/helper/uys")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUniversitaryYears() {
		List<UniversitaryYear> uys = service.getUniversitaryYears();
		
		return Response.ok(uys).status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600").build();
	}
	
	// USED
	@GET
	@Path("/internship/per-country")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentInternshipPerCountry(@QueryParam("uni") long uniId)
	{
		Map<String, List<Student>> out = service.getStudentInternshipPerCountry(uniId);
				
		return Response.ok(out).status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Max-Age", "1209600").build();
		
	}


}
