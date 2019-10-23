package com.internspace.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPFileEJBLocal;
import com.internspace.entities.fyp.FYPFile;

public class FYPFileEJB implements FYPFileEJBLocal{
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void addFYPFile(FYPFile file) {
		System.out.println("Adding: " + file);
		em.persist(file);
		
	}

	@Override
	public List<FYPFile> getAll() {
		return em.createQuery("SELECT f from FYPFile f").getResultList();
	}

}
