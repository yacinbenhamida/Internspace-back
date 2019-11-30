package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.users.Employee;
import com.internspace.entities.users.User;

@Local
public interface UserEJBLocal {
	public User verifyLoginCredentials(String username, String password);
	public User getUserByUsername(String username);
	public List<Employee> getTeachersOFdept(long idDept);
}
