package com.internspace.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public List<Student> getStudentsLocationDistribution(long uniId, boolean abroad) {
		
		University uni = em.find(University.class, Long.valueOf(uniId));

		if (uni == null)
			return null;
		
		String op = abroad ? " <> " : " = ";
		List<Student> students = em.createQuery("from " + Student.class.getName() + " s WHERE s.internship.location " + op + " :location AND s.studyClass.classYear = :fypYear", Student.class)
				.setParameter("location", uni.getLocation())
				.setParameter("fypYear", uni.getFypClassYear())
				.getResultList();

		return students;
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

			List<Student> students = em.createQuery("FROM " + Student.class.getName() + " s WHERE s.studyClass.classYear = :fypYear", Student.class)
					.setParameter("fypYear", uni.getFypClassYear())
					.getResultList();
			
			float allCount = students.size();
			
			// Use of existing service ✌
			students = getStudentsLocationDistribution(uniId, true);

			float foundCount = students.size();
			
			float distribution = foundCount / allCount;
			
			System.out.println(allCount);
			System.out.println(foundCount);
			
			out.put(uyId, distribution);
		}

		return out;
	}
	
}
