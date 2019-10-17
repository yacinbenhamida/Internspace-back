package com.internspace.entities.users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.university.Site;

@Entity
@Table(name="employee")
public class Employee extends User {

	private static final long serialVersionUID = 1L;
	public enum Role{
		internshipDirector,
		departmentHead,
		teacher,
		admin,
		superAdmin,
		
	}
	
	String password;
	String username;
	Date birthDate;
	Role role;
	
	/*
	 * Associations
	 */
	
	// Only use this when role == internshipDirector
	@OneToOne(mappedBy = "internshipDirector")
	Site site;
	
	/*
	 * Getters & Setters
	 */

}

