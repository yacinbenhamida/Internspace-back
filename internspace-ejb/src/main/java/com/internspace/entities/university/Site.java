package com.internspace.entities.university;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	Departement departement;
	
	/*
	 * Getters & Setters
	 */

}