package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.TeacherEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFile;



@Stateless
public class TeacherEJB implements TeacherEJBLocal {
	@PersistenceContext
	EntityManager em;
	

	public List<FYPFile> getPendingFYPFiles() {
		
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f").getResultList();

	}

	
	public boolean PrevalidateFYPFile(FYPFile file) {
		return false;
	}


	public List<FYPFile> getSupervisedFYPfiles() {
	
		return null;
	}

	
	public List<FYPFile> getprotractoredFYPfiles() {
		return null;
	}


	public boolean ValidateMajorModification(FYPFile f) {
		return false;
	}

	public void ProposeFYPCategory(FYPCategory F) {
		System.out.println("adding category");
em.persist(F);		
	}

}
