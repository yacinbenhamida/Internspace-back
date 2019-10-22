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



@Stateless
public class TeacherEJB implements TeacherEJBLocal {
	@PersistenceContext
	EntityManager em;
	
@Override
	public List<FYPFile> getPendingFYPFiles() {
		
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f where status=pending ").getResultList();

	}

@Override
	public boolean PrevalidateFYPFile(FYPFile file) {
		return false;	
	}

@Override
	public List<FYPFile> getSupervisedFYPfiles(int id) {
	
	
		return em.createQuery("FROM"+ FYPFile.class.getName() + "f where f.fyp_file_id =(SELECT"+ FYPIntervention.class.getName()+".internshipSheet_fyp_file_id FROM"+ FYPIntervention.class.getName()+" n WHERE n.teacher_user_id=:id)").setParameter("id",id).getResultList();
	}

@Override
	public List<FYPFile> getprotractoredFYPfiles() {
		return null;
	}

@Override
	public FYPFile ValidateMajorModification(FYPFile f) {
		if (f.getFileStatus().equals("pending"))
		{
		em.merge(f);
		em.flush();
		 return em.find(FYPFile.class, f.getId());
		}
		return null;
	}
@Override
	public void ProposeFYPCategory(FYPCategory F) {
		System.out.println("adding category");
em.persist(F);		
	}

}
