package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Set;

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
	
	/*
	 * Associations
	 */
	
	@OneToMany(mappedBy = "university")
	Set<Departement> departements;
	
	/*
	 * Getters & Setters
	 */

}

