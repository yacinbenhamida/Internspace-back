package com.internspace.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.DepartementEJBLocal;

@Stateless
public class DepartementEJB implements DepartementEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public int setDepartmentDirector(long idDepartement, long idEmployee) {
		Query query = em.createQuery("UPDATE Departement d SET d.departementHead=:idEmployee"
				+ " WHERE d.id=:idDepartement ");
		query.setParameter("idEmployee", idEmployee);
		query.setParameter("idDepartement", idDepartement);
		query.executeUpdate();
		return 1;
	}

}
