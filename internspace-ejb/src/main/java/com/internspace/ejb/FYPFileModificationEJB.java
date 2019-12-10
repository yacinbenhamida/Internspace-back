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
		f.setIsChanged(false);
		f.setIsConfirmed(false);
	
		
		f.setFyp(file);
		
		f.setFeatures(set);
		
		em.persist(f);
		return em.find(FYPFile.class, file.getId());
	}

	@Override
	public List<FYPFile> getAllFilesModification() {
		return em.createQuery("SELECT f.fyp from FYPFileModification f ").getResultList();
		
	}

	@Override
	public FYPFile editFYPSheet(FYPFile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FYPFileModification> getAll() {
		return em.createQuery("SELECT f from FYPFileModification f where f.isChanged =:id").setParameter("id", true).getResultList();
	}

	@Override
	public boolean acceptModification(long id) {
		
		FYPFileModification fm = em.find(FYPFileModification.class, id);
		fm.setIsConfirmed(true);
		em.persist(fm);
		em.flush();

		return true;
	}

	

}
