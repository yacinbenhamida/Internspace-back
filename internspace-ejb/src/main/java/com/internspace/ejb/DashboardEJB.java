package com.internspace.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.internspace.ejb.abstraction.DashboardEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.Internship;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.university.University;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Student;

@Stateless
public class DashboardEJB implements DashboardEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Student> getStudentsBySite(long siteId) {

		List<Student> students = em.createQuery("from " + Student.class.getName() + " s WHERE s.studyClass.departement.site.id = :siteId", Student.class)
			.setParameter("siteId", siteId)
			.getResultList();

		return students;
	}
	
	@Override
	public float getStudentsLocalAbroadDistribution(long uniId, boolean abroad) {
		List<Student> thisYearsStudents = getFypStudentsByUY(uniId, -2, false, false, null, false);
		
		float dist = getFypStudentsByUY(uniId, -2, true, !abroad, null, false).size();
		
		System.out.println(dist);
		System.out.println(thisYearsStudents != null ? thisYearsStudents.size(): "X");
		
		return dist / thisYearsStudents.size();
	}
	

	@Override
	public Map<Long, Float> getAbroadPercentagePerYear(long uniId)
	{
		// UY ID to Abroad Distribution.
		Map<Long, Float> out = new HashMap<Long, Float>();
		
		University uni = em.find(University.class, Long.valueOf(uniId));

		if (uni == null)
			return null;
		
		List<UniversitaryYear> uys = em.createQuery("FROM " + UniversitaryYear.class.getName(), UniversitaryYear.class)
				.getResultList();
		
		for(UniversitaryYear e : uys)
		{
			long uyId = e.getId();

			System.out.println("uniYear: " + uyId + " | studyClassYear: " + uni.getFypClassYear() + " | uniId: " + uniId);
			
			// Get all relevant to the university, in which they can perform a FYP, in that UY
			List<Student> students = em.createQuery("FROM " + Student.class.getName() + " s"
					+ " WHERE s.studyClass.universitaryYear.id = :uniYear"
					+ " AND s.studyClass.classYear = :studyClassYear"
					+ " AND s.studyClass.departement.site.university.id = :uniId", Student.class)
					.setParameter("uniYear", uyId)
					.setParameter("studyClassYear", uni.getFypClassYear())
					.setParameter("uniId", uniId)
					.getResultList();
			
			float allCount = students.size();
			
			System.out.println("got allCount: " + allCount);
			
			if (allCount == 0)
				continue;
			
			// Use of existing service ✌
			// students = getStudentsLocationDistribution(uniId, true);			
			students = getFypStudentsByUY(uniId, uyId, true, false, null, false);
			
			float foundCount = students.size();
			
			float distribution = foundCount / allCount;
			
			System.out.println(allCount);
			System.out.println(foundCount);
			
			out.put(uyId, distribution);
		}

		return out;
	}

	@Override
	public float getStudentsDistributionByLocationAndUY(long uniId, String location, long uyId) {
		List<Student> uyStudents = getFypStudentsByUY(uniId, uyId, false, false, null, false);
		
		List<Student> distStudents = getFypStudentsByUY(uniId, uyId, false, false, location, false);
		
		System.out.println(distStudents);
		
		if(uyStudents == null || distStudents == null)
			return 0f;
		
		System.out.println(distStudents.size());
		System.out.println(uyStudents.size());
		
		return distStudents.size() / uyStudents.size();
	}

	@Override
	public List<Company> getMostCompanyAcceptingInternsWithUniversity(long uniId, int n) {
		
		String queryStr = "FROM " + Company.class.getName() + " C"
				//+ " JOIN FETCH C.internships INS WHERE INS.student.studyClass.departement.site.university.id = :uniId"
				+ " WHERE size(C.internships) <> 0"
				+ " ORDER BY size(C.internships) DESC";
		
		TypedQuery<Company> query = em.createQuery(queryStr, Company.class);
				//.setParameter("uniId", uniId);
		
		// Acts like TOP in classical SQL query.
		List<Company> companies = query.setMaxResults(n).getResultList();
		companies.stream().forEach(x -> {x.setInternships(null);});
		
		return companies;
	}

	@Override
	public int getInternshipsByCategory(long uniId, long categoryId) {
		FYPCategory category = em.find(FYPCategory .class, Long.valueOf(categoryId));
		String queryStr = "FROM " + Internship.class.getName() + " i WHERE"
				+ " i.student.studyClass.departement.site.university.id = :uniId"
				+ " AND :category MEMBER OF i.subject.categories"
				;
		
		TypedQuery<Internship> query = em.createQuery(queryStr, Internship.class)
				.setParameter("category", category)
				.setParameter("uniId", uniId);
			
		List<Internship> out = query.getResultList();
		
		System.out.println(out);
		
		return out.size();
	}
	
	@Override
	public List<FYPCategory> getMostRequestedCategoriesByCompanies() {
		
		String queryStr = "SELECT C FROM " + FYPCategory.class.getName() + " C"
				+ " JOIN FETCH C.subjects S"
				//+ " ORDER BY size(C.subjects) DESC"
				;
		
		TypedQuery<FYPCategory> query = em.createQuery(queryStr, FYPCategory.class);
			
		List<FYPCategory> out = query.getResultList();
		//out.stream().forEach(e -> e.getSubjects().stream().forEach(s -> s.setCategories(null)));
		
		return out;
	}

	@Override
	public List<FYPCategory> getMostRequestedCategoriesByStudentsOfUni(long uniId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// PRIVATE METHODS

	/***
	 * 
	 * @param uniId University ID
	 * @param uyId -1 means current UY, -2 means all, else specified
	 * @param onlyAbroad will return only abroad, else all relevant to other parameters...
	 * @param reverseFilter if onlyAboard is true, then this will reverse it to only local
	 * @return
	 */
	private List<Student> getFypStudentsByUY(long uniId, long uyId, boolean onlyAbroad, boolean reverseFilter, String location, boolean excludeLocation) {
		
		University uni = em.find(University.class, Long.valueOf(uniId));

		System.out.println("GET FYP STUDENT BY UY PARAMS:");
		System.out.println(("uniId: " + uniId + " | uyId: " + uyId + " | onlyAbroad: " + onlyAbroad + " | reverseFilter: " + reverseFilter + " | location: " + location + " | exclude : " + excludeLocation));
		
		if (uni == null)
			return null;
		
		String queryStr = "FROM " + Student.class.getName() + " s WHERE"
				+ " s.studyClass.classYear = :studyClassYear"
				+ " AND s.studyClass.departement.site.university.id = :uniId"
				;
		
		// Current UY?
		//queryStr = uyId != -1 ? queryStr + " AND s.studyClass.universitaryYear.id = :uniYear" : queryStr;
		
		if(uyId != -2)
		{
			queryStr = queryStr + " AND s.studyClass.universitaryYear.id = :uniYear";
		}
		
		// Reverse abroad/local filtering?
		String onlyAbroad_OP = reverseFilter ? " = " : " <> ";
		String onlyLocation_OP = excludeLocation ? " <> " : " = ";
		
		// Apply filtering?
		if (onlyAbroad )
			queryStr = queryStr + " AND s.internship.location " + onlyAbroad_OP + " :location";
		else if(location != null) // Specific Internship Location?
			queryStr = queryStr + " AND s.internship.location " + onlyLocation_OP + " :location";
		
		List<Student> students;
		TypedQuery<Student> query = em.createQuery(queryStr, Student.class)
				.setParameter("studyClassYear", uni.getFypClassYear())
				.setParameter("uniId", uniId);
			
		// Set parameters
		if(uyId != -2)
		{
			query = uyId != -1 ? query.setParameter("uniYear", uyId) : 
				query.setParameter("uniYear", uni.getCurrentUniversitaryYear().getId());
		}
		
		query = onlyAbroad ? query.setParameter("location", uni.getLocation()) : query;

		// Apply filtering?
		if (onlyAbroad)
			query = query.setParameter("location", uni.getLocation());
		else if(location != null) // Specific Internship Location?
			query = query.setParameter("location", location);
		
		students = query.getResultList();
		
		return students;
 	}



}
