package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPFeaturesEJBLocal;
import com.internspace.entities.fyp.FYPFeature;

@Stateless
public class FYPFeaturesEJB implements FYPFeaturesEJBLocal {

	
	@PersistenceContext
	EntityManager service;

	@Override
	public List<FYPFeature> ListFYPFeature() {
		return service.createQuery("SELECT f from FYPFeature f ").getResultList();
	}

	@Override
	public void addFYPFeature(FYPFeature file) {

		
		service.persist(file);
		
	}
}
