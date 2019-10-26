package com.internspace.ejb.abstraction;

import java.util.List;

import com.internspace.entities.university.Departement;

public interface DepartementEJBLocal {
	int addDepartment(Departement departement);
	public List<Departement> getAllDepartments();
	int updateDepartment(Departement departement);
	int deleteDepartment(long id);
}
