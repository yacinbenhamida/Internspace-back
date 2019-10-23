package com.internspace.ejb.abstraction;


public interface ManageEmployeeRolesEJBLocal {
	int setDepartmentDirector(long idDepartement, long idEmployee);
	int setSiteInternshipDirector(long idSite, long idEmployee);
}
