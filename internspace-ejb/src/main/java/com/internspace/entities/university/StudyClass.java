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
import javax.persistence.Table;

import com.internspace.entities.users.Student;

/*
 * Defines a set of students studying together, a specific ClassOption.
 * Useful when selecting students by studied class-option type...
 */
@Entity
@Table(name="study_class")
public class StudyClass implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="study_class_id")
	long id;
	
	String name;
	int classYear; // Values like (1, 2, 3, 4, 5, ...), 5 means 5éme année...
	
	@ManyToOne
	@JoinColumn(name = "universitary_year_id")
	UniversitaryYear universitaryYear;
	
	/*
	 * Associations
	 */
	
	@OneToMany(mappedBy = "studyClass")
	Set<Student> students;
	
	@ManyToOne
	Departement departement;

	@ManyToOne
	ClassOption classOption;
	
	/*
	 * Getters & Setters
	 */

}