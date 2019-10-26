package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.DepartementEJBLocal;
import com.internspace.entities.university.Departement;
import com.internspace.entities.university.University;

@Stateless
public class DepartementEJB implements DepartementEJBLocal {

	@PersistenceContext
	EntityManager em;

	@Override
	public int addDepartment(Departement departement) {
		System.out.println("addDepartment: " + departement);
		em.persist(departement);
		return 1;
	}


	@Override
	public List<Departement> getAllDepartments() {
		return em.createQuery("SELECT d from Departement d").getResultList();
	}

	@Override
	public int updateDepartment(Departement departement) {
		em.merge(departement);
		return 1;
	}

	@Override
	public int deleteDepartment(long id) {
		Departement d = em.find(Departement.class, id);
		if(d != null) {
			em.remove(d);
			return 1;
		}
		return 0;
	}
	


}
