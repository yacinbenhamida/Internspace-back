package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.DashboardEJBLocal;
import com.internspace.entities.university.University;
import com.internspace.entities.users.Student;

@Stateless
public class DashboardEJB implements DashboardEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Student> getStudentsLocationDistribution(int uniId, boolean abroad) {
		
		University uni = em.find(University.class, Long.valueOf(uniId));

		if (uni == null)
			return null;
		
		String op = abroad ? " <> " : " = ";
		List<Student> students = em.createQuery("from " + Student.class.getName() + " s WHERE s.internship.location " + op + " :location", Student.class).setParameter("location", uni.getLocation()).getResultList();

		return students;
	}
	
	@Override
	public List<Student> getFypStudentsBySite(int siteId) {
		return null;
	}
	
}
