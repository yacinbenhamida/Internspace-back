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

import com.internspace.entities.users.Student;

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
	
	String name;

	/*
	 * Associations
	 */
	
	@ManyToOne
	Site site;
	
	@OneToMany(mappedBy = "classroom")
	Set<Student> students;
	
	/*
	 * Getters & Setters
	 */

}