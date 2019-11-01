package com.internspace.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPFileModificationEJBLocal;
import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileModification;
import com.internspace.entities.fyp.FYPSheetHistory;
import com.internspace.entities.users.Student;

public class FYPFileModificationEJB implements FYPFileModificationEJBLocal{
	
	
	
	@PersistenceContext
	EntityManager em;

	@Override
	public FYPFile addFYPSheet(FYPFile file) {
		
		 FYPFileModification f = new  FYPFileModification();
		List<FYPFeature> ff =  ( em.createQuery("SELECT f.features from "+file.getClass().getName()+" f  ")).getResultList();
	
        Set<FYPFeature> set = new HashSet<FYPFeature>(ff);

		f.setProblematic(file.getProblematic());
		f.setFyp(file);
		
		f.setFeatures(set);
		
		em.persist(f);
		return em.find(FYPFile.class, file.getId());
	}

}
