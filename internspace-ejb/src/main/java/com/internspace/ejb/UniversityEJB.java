package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.UniversityEJBLocal;
import com.internspace.entities.university.University;

@Stateless
public class UniversityEJB implements UniversityEJBLocal{

	@PersistenceContext
	EntityManager em;

	@Override
	public int addUniversity(University university) {
		System.out.println("addUniversity: " + university);
		em.persist(university);
		return 1;
	}

	@Override
	public int updateUniversity(University university) {
		em.merge(university);
		return 1;
	}

	@Override
	public int deleteUniversity(long id) {
		University u = em.find(University.class, id);
		if(u != null) {
			em.remove(u);
			return 1;
		}
		return 0;
	}

	@Override
	public List<University> getAllUniversities() {
		return em.createQuery("SELECT u from University u").getResultList();
	}
	
	@Override
	public University searchUniversity(long id) {
		return em.find(University.class, id);
	}
	
		
		
}
