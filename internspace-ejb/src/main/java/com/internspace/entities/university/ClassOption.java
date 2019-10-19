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
import javax.persistence.Table;


@Entity
@Table(name="class_option")
public class ClassOption implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="class_option_id")
	long id;
	
	String name;
	int requiredScore;
	
	/*
	 * Associations
	 */
	
	@ManyToOne
	Departement departement;
	
	@OneToMany(mappedBy = "classOption")
	Set<StudyClass> studyClasses;
	
	/*
	 * Getters & Setters
	 */

}