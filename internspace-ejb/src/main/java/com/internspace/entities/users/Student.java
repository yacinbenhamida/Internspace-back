package com.internspace.entities.users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student extends User {

	private static final long serialVersionUID = 1L;
	
	String password;
	String username;
	Date birthDate;
	
	/*
	 * Getters & Setters
	 */

}

