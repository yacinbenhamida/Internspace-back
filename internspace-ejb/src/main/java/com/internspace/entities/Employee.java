package com.internspace.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee extends User {

	private static final long serialVersionUID = 1L;
	public enum Role{
		departmentDirector,
		teacher,
		admin,
		superAdmin,
		
	}
	
	String password;
	String username;
	Date birthDate;
	
	/*
	 * Getters & Setters
	 */

}

