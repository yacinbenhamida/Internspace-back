package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.users.Employee;

@Entity
@Table(name="site")
public class Site implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="site_id")
	long id;
	
	String name;

	/*
	 * Associations
	 */
	
	@ManyToOne
	@JoinColumn(name = "uni_id")
	University university;
	
	@OneToMany(mappedBy = "site")
	Set<Departement> departements;
	
	// Explicitly check if this employee has InternshipDirector role.
	@OneToOne(mappedBy = "site")
	@JoinColumn(name = "internship_director_id")
	Employee internshipDirector;
	
	/*
	 * Getters & Setters
	 */

}