package com.internspace.ejb.abstraction;

import java.util.List;

import com.internspace.entities.university.Departement;

public interface DepartementEJBLocal {
	long addDepartment(Departement departement);
	public List<Departement> getAllDepartments();
	public List<Departement> getAllDepartmentsOfSite(long siteId);
	int updateDepartment(Departement departement);
	int deleteDepartment(long id);
	Departement getDeptById(long id);
}
