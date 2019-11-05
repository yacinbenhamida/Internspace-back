package com.internspace.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	@Override
	public void addFYPFeatures(Set<FYPFeature> file) {
		List ll = new ArrayList<FYPFeature>(file);
		
		for(int i=0;i< ll.size();i++) {
			service.persist(ll.get(i));
		}
		
		/*for(FYPFeature i : file) {
			service.persist(i);
		}*/
		
		
	}
}
