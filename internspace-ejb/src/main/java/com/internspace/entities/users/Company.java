package com.internspace.entities.users;

import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.Internship;
import com.internspace.entities.fyp.InternshipConvention;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company extends User {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */
	
	//... email, lastname and firstname for responsible are the User's
	String website;
	String address;
	String country;
	String supervisorEmail;
	String phoneNumber;
	
	/*
	 * Associations
	 */
	
	@ManyToMany
	Set<FYPSubject> subjects;

	@OneToMany(mappedBy = "company")
	Set<Internship> internships;

	@OneToMany(mappedBy = "company")
	Set<InternshipConvention> internshipConventions;
	
	/*
	 * Getters & Setters
	 */
	
	public Set<FYPSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<FYPSubject> subjects) {
		this.subjects = subjects;
	}
}

