package com.internspace.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.internspace.ejb.abstraction.DashboardEJBLocal;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.university.University;
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
	public float getStudentsLocationDistribution(long uniId, boolean abroad) {
		List<Student> thisYearsStudents = getFypStudentsByUY(uniId, -1, false, false);
		
		float dist = getFypStudentsByUY(uniId, -1, true, !abroad).size();
		
		System.out.println("Dist: " + dist + " | Count: " + thisYearsStudents);
		
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
			students = getFypStudentsByUY(uniId, uyId, true, false);
			
			float foundCount = students.size();
			
			float distribution = foundCount / allCount;
			
			System.out.println(allCount);
			System.out.println(foundCount);
			
			out.put(uyId, distribution);
		}

		return out;
	}
	
	// PRIVATE METHODS

	/***
	 * 
	 * @param uniId University ID
	 * @param uyId -1 means current UY
	 * @param onlyAbroad will return only abroad, else all relevant to other parameters...
	 * @param reverseFilter if onlyAboard is true, then this will reverse it to only local
	 * @return
	 */
	private List<Student> getFypStudentsByUY(long uniId, long uyId, boolean onlyAbroad, boolean reverseFilter) {
		
		University uni = em.find(University.class, Long.valueOf(uniId));

		if (uni == null)
			return null;
		
		String queryStr = "from " + Student.class.getName() + " s WHERE"
				+ " s.studyClass.classYear = :studyClassYear"
				+ " AND s.studyClass.departement.site.university.id = :uniId"
				+ " AND s.studyClass.universitaryYear.id = :uniYear";
		// Current UY?
		//queryStr = uyId != -1 ? queryStr + " AND s.studyClass.universitaryYear.id = :uniYear" : queryStr;
		
		// Reverse abroad/local filtering?
		String op = reverseFilter ? " = " : " <> ";
		
		// Apply filtering?
		queryStr = onlyAbroad ? queryStr + " AND s.internship.location " + op + " :location": queryStr;
		
		List<Student> students;
		TypedQuery<Student> query = em.createQuery(queryStr, Student.class)
				.setParameter("studyClassYear", uni.getFypClassYear())
				.setParameter("uniId", uniId);
			
		// Set parameters
		query = uyId != -1 ? query.setParameter("uniYear", uyId) : 
			query.setParameter("uniYear", uni.getCurrentUniversitaryYear().getId());
		
		query = onlyAbroad ? query.setParameter("location", uni.getLocation()) : query;

		students = query.getResultList();
		
		return students;
	}

}
