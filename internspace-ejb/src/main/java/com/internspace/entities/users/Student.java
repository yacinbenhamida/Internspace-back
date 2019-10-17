package com.internspace.entities.users;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.fyp.Internship;
import com.internspace.entities.university.Classroom;

@Entity
@Table(name="student")
public class Student extends User {

	private static final long serialVersionUID = 1L;

	int classYear;
	
	/*
	 * Associations
	 */

	@OneToOne
	Internship internship;
	
	@ManyToOne
	Classroom classroom;
	
	/*
	 * Getters & Setters
	 */

}

