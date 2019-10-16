package com.internspace.entities.users;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="employee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee extends User implements Serializable{

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

