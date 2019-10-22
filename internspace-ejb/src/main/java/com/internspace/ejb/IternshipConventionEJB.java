package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.InternshipConventionEJBLocal;

import com.internspace.entities.fyp.InternshipConvention;


@Stateless
public class IternshipConventionEJB implements InternshipConventionEJBLocal {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void addInternshipConvention(InternshipConvention inter) {
		
		System.out.println("Adding: " + inter);
		em.persist(inter);
	
	
	}

	@Override
	public List<InternshipConvention> getAllInternshipConvention() {
		
		return em.createQuery("SELECT c from InternshipConvention c").getResultList();
		
	}

}
