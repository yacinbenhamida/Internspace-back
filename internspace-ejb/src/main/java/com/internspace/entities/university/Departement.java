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
import com.internspace.entities.users.Teacher;

@Entity
@Table(name="department")
public class Departement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="departement_id")
	long id;
	
	String name;
	
	@Column(name = "rapporteur_no_of_actions_allowed")
	int numberOfActionsAllowedForProtractors; 		//this is edited by the internships director.
	@Column(name = "supervisors_no_of_actions_allowed")
	int numberOfActionsAllowedForSupervisors; 		//this is edited by the internships director.
	@Column(name = "prevalidatiors_no_of_actions_allowed")
	int numberOfActionsAllowedForPreValidators; 	//this is edited by the internships director.
	@Column(name = "president_no_of_actions_allowed")
	int numberOfActionsAllowedForPresidents; 		//this is edited by the internships director.
	
	/*
	 * Associations
	 */
	
	@ManyToOne
	Site site;
	
	@OneToMany(mappedBy = "departement")
	Set<Classroom> classrooms;
	
	// Explicitly check if this employee has DepartmentHead role.
	@OneToOne
	Employee departementHead;

	// Explicitly check if this employee has the Teacher role.
	@OneToMany(mappedBy = "departement")
	Set<Teacher> teachers;
	
	@OneToMany(mappedBy = "departement")
	Set<ClassOption> classOptions;
	
	/*
	 * Getters & Setters
	 */
	public Departement() {
		// TODO Auto-generated constructor stub
	}
	
}