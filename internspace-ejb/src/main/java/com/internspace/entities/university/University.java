package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="university")
public class University implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="university_id")
	long id;
	
	String name;
	int fypClassYear; // What class year is considered to be final-year-project year? 5 for example...
	String location; // School's location, useful to retrieve abroad internships...
	
	/*
	 * Associations
	 */
	
	//@OneToMany(mappedBy = "university")
	//Set<Departement> departements;
	@OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
	Set<Site> sites;
	
	
	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFypClassYear() {
		return fypClassYear;
	}

	public void setFypClassYear(int fypClassYear) {
		this.fypClassYear = fypClassYear;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


}

