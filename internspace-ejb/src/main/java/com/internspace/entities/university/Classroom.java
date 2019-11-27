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

/*
 * Keeps track of whether the classroom is scheduled for a future FYP Defense or not.
 * Useful when trying to split FYP Defenses to classroom by date and hour...
 */
@Entity
@Table(name="classroom")
public class Classroom implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="classroom_id")
	long id;
	
	@Column(name="name")
	String name;
	

	/*
	 * Associations
	 */
	
	@ManyToOne
	Departement departement;
	
	@OneToMany(mappedBy = "classroom")
	Set<DefenseSchedule> schedules;

	/*
	 * Getters & Setters
	 */

}