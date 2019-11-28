package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.TeacherEJBLocal;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPFileModification;



@Stateless
public class TeacherEJB implements TeacherEJBLocal {
	@PersistenceContext
	EntityManager em;
	
@Override
	public List<FYPFile> getPendingFYPFiles() {
		
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f where f.fileStatus = :status").setParameter("status", FYPFileStatus.pending).getResultList();
	}		

@Override
	public FYPFile  PrevalidateFYPFile( long id ) {
	FYPFile f = em.find(FYPFile.class, id);
	f.setIsPrevalidated(Boolean.TRUE);
	em.persist(f);
	return f;
	
	}

@Override 
	public List<FYPFile> getSupervisedFYPfiles(long id) {
		return em.createQuery("FROM "+FYPFile.class.getName()+" f WHERE f.id IN (SELECT n.fypFile.id FROM "+FYPIntervention.class.getName()+" n WHERE n.teacher.id=:id and n.teacherRole like 'supervisor	')").setParameter("id",id).getResultList();
	}

@Override
	public List<FYPFile> getprotractoredFYPfiles(long id) {
	return em.createQuery("FROM "+FYPFile.class.getName()+" f WHERE f.id IN (SELECT n.fypFile.id FROM "+FYPIntervention.class.getName()+" n WHERE n.teacher.id=:id and n.teacherRole like 'reporter')").setParameter("id",id).getResultList();
	}

@Override
	public void ValidateMajorModification(long id ,long id2) {
	FYPFile f = em.find(FYPFile.class, id);
	FYPFileModification ff=em.find(FYPFileModification.class, id2);
	ff.setIsChanged(Boolean.TRUE);
	ff.setIsConfirmed(Boolean.TRUE);
	//f.setFeatures(ff.getFeatures());
	f.setProblematic(ff.getProblematic());
	em.merge(f);
	em.merge(ff);	
	em.flush();
	}
@Override
	public void ProposeFYPCategory(FYPCategory F) {
em.persist(F);		
	}

@Override
public List<FYPFile> getPrevalidatedFiles(long id) {
	return em.createQuery("FROM " + FYPFile.class.getName()  + " f where f.isPrevalidated=true  and f.id IN (SELECT n.fypFile.id FROM "+FYPIntervention.class.getName()+" n WHERE n.teacher.id=:id , n.teacherRole='supervisor'").setParameter("id",id).getResultList();
}

@Override
public List<FYPCategory> getAllCategories() {
	
	return em.createQuery("FROM " + FYPCategory.class.getName()).getResultList();

}



}
