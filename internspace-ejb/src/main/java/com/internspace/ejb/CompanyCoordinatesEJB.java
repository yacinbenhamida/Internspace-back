package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.Response;

import com.internspace.ejb.abstraction.CompanyCoordinatesEJBLocal;
import com.internspace.entities.university.CompanyCoordinates;

@Stateless
public class CompanyCoordinatesEJB implements CompanyCoordinatesEJBLocal {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void addCompanyCoordinates(CompanyCoordinates C) {
			em.persist(C);
			em.flush();
			System.out.println("add to the base");
	}

	@Override
	public void deleteCompanyCoordinates(long id) {
		CompanyCoordinates cc = em.find(CompanyCoordinates.class, id);
		em.remove(cc);
		
	}

	@Override
	public void updateCompanyCoordinates(CompanyCoordinates c) {
		em.merge(c);
		System.out.println("updated");
		
	}

	@Override
	public List<CompanyCoordinates> listeCompanyCoordinates() {
		
		return em.createQuery("SELECT c FROM CompanyCoordinates c").getResultList();
	}

	

	
}
