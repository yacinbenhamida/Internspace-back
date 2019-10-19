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
	
	//... email, lastname and firstname for responsible are the User's (from Parent)
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Internship> getInternships() {
		return internships;
	}

	public void setInternships(Set<Internship> internships) {
		this.internships = internships;
	}

	public Set<InternshipConvention> getInternshipConventions() {
		return internshipConventions;
	}

	public void setInternshipConventions(Set<InternshipConvention> internshipConventions) {
		this.internshipConventions = internshipConventions;
	}
	
	
}

