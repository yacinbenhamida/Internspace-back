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
		String ch="pending";
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f where f.isPrevalidated=:x ").setParameter("x", false).getResultList();
	}

@Override
	public void  PrevalidateFYPFile( long id ) {
	FYPFile f = em.find(FYPFile.class, id);
	f.setIsPrevalidated(Boolean.TRUE);
	em.merge(f);
	em.flush();
	
	}

@Override 
	public List<FYPFile> getSupervisedFYPfiles(long id) {
		return em.createQuery("FROM "+FYPFile.class.getName()+" f WHERE f.id IN (SELECT n.fypFile.id FROM "+FYPIntervention.class.getName()+" n WHERE n.teacher.id=:id and n.teacherRole like 'supervisor')").setParameter("id",id).getResultList();
	}

@Override
	public List<FYPFile> getprotractoredFYPfiles(long id) {
	return em.createQuery("FROM "+FYPFile.class.getName()+" f WHERE f.id IN (SELECT n.fypFile.id FROM "+FYPIntervention.class.getName()+" n WHERE n.teacher.id=:id and n.teacherRole like 'reporter')").setParameter("id",id).getResultList();
	}

@Override
	public void ValidateMajorModification(long id ) {
	FYPFile F=em.find(FYPFile.class, id);
	FYPFileModification ff=em.find(FYPFileModification.class, id);
	F.setFeatures(ff.getFeatures());
	ff.setIsChanged(Boolean.TRUE);
	ff.setIsConfirmed(Boolean.TRUE);
		
	}
@Override
	public void ProposeFYPCategory(FYPCategory F) {

em.persist(F);		
	}

}
