package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name="name")
	String name;
	@Column(name="address")
	String address;

	/*
	 * Associations
	 */
	@ManyToOne
	University university;
	
	@OneToOne
	Employee internshipDirector;
	
	@ManyToOne
	Departement departement;
	
	@OneToMany(mappedBy = "site")
	Set<Classroom> classrooms;
	
	/*
	 * Getters & Setters
	 */

}