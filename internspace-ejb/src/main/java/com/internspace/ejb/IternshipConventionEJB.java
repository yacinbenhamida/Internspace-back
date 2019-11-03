package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.InternshipConventionEJBLocal;

import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.university.University;
import com.internspace.entities.users.Student;


@Stateless
public class IternshipConventionEJB implements InternshipConventionEJBLocal {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void addInternshipConvention(InternshipConvention inter,long id) {

		Student std = em.find(Student.class, id);
		if(std!= null) {
			if(std.getInternshipConvention() == null) {
				inter.setStudent(std);
				em.persist(inter);
				}
		
		
		}
	
	
	}

	@Override
	public List<InternshipConvention> getAllInternshipConvention() {
		
		return em.createQuery("SELECT c from InternshipConvention c").getResultList();
		
	}
	@Override
	public int removeConvention(long  id) {
		
		InternshipConvention u = em.find(InternshipConvention.class, id);
	
		System.out.println("Debug : "+u);
		if(u != null ) {
			
			em.remove(u);
			return 1;
		}
		return 0;
		
	}

}
