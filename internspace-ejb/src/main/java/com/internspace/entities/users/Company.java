package com.internspace.entities.users;

import com.internspace.entities.fyp.FYPSubject;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company extends User {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */
	
	@ManyToMany
	Set<FYPSubject> subjects;

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

