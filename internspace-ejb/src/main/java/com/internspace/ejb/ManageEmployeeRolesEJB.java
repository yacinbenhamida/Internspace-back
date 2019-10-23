package com.internspace.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.ManageEmployeeRolesEJBLocal;
import com.internspace.entities.university.Departement;
import com.internspace.entities.university.Site;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Employee.Role;

@Stateless
public class ManageEmployeeRolesEJB implements ManageEmployeeRolesEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public int setDepartmentDirector(long idDepartement, long idEmployee) {
		Employee e = em.find(Employee.class, idEmployee);
		Departement d = em.find(Departement.class, idDepartement);
		if(e != null && d != null) {
			e.setRole(Role.departmentHead);
			Query query = em.createQuery("UPDATE Departement d SET d.departementHead=:Employee"
					+ " WHERE d.id=:idDepartement ");
			query.setParameter("Employee", e);
			query.setParameter("idDepartement", d.getId());
			query.executeUpdate();
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int setSiteInternshipDirector(long idSite, long idEmployee) {
		Employee e = em.find(Employee.class, idEmployee);
		Site s = em.find(Site.class, idSite);
		if(e != null && s != null) {
			e.setRole(Role.internshipsDirector);
			Query query = em.createQuery("UPDATE Site s SET s.internshipDirector=:Employee"
					+ " WHERE s.id=:idSite ");
			query.setParameter("Employee", e);
			query.setParameter("idSite", s.getId());
			query.executeUpdate();
			return 1;
		} else {
			return 0;
		}
	}

	
}
