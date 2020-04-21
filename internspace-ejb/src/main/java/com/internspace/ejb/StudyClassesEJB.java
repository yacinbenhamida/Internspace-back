package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.StudyClassesEJBLocal;
import com.internspace.entities.university.ClassOption;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.university.UniversitaryYear;
@Stateless
public class StudyClassesEJB implements StudyClassesEJBLocal {
	@PersistenceContext
	EntityManager em;
	@Override
	public StudyClass addClass(StudyClass c,int endYear) {
		UniversitaryYear year = (UniversitaryYear) em.createQuery("SELECT y from UniversitaryYear y where y.endDate = :val")
				.setParameter("val", endYear).getResultList().get(0);
		c.setUniversitaryYear(year);
		em.persist(c);
		return em.find(StudyClass.class, c.getId());
	}

	@Override
	public List<StudyClass> getClassesOfDepartment(long depId,int endYear) {
		return em.createQuery("select s from StudyClass s where s.classOption.departement.id = :id"
				+ " AND s.universitaryYear.endDate = :end").setParameter("id", depId)
				.setParameter("end", endYear).getResultList();
	}

	@Override
	public List<ClassOption> getAllClassOptionsOfDept(long depId) {
		return em.createQuery("SELECT c from ClassOption c where c.departement.id = :id").setParameter("id", depId)
				.getResultList();
	}
	@Override
	public List<UniversitaryYear> getRegisteredUniYears() {
		return em.createQuery("SELECT c from UniversitaryYear c ")
				.getResultList();
	}

	@Override
	public StudyClass getClassById(int id) {
		return em.find(StudyClass.class,(long) id);
	}
}
