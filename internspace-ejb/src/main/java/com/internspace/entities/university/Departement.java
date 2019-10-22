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
	@JoinColumn(name = "site_id")
	Site site;
	
	@OneToMany(mappedBy = "departement")
	Set<Classroom> classrooms;
	
	// Explicitly check if this employee has DepartmentHead role.
	@OneToOne
	@JoinColumn(name = "departement_head_id")
	Employee departementHead;

	// Explicitly check if this employee has the Teacher role.
	@OneToMany(mappedBy = "department")
	Set<Employee> teachers;
	
	@OneToMany(mappedBy = "departement")
	Set<ClassOption> classOptions;
	
	@ManyToOne
	@JoinColumn(name = "university_id")
	University university;
	/*
	 * Getters & Setters
	 */
	public Departement() {
		// TODO Auto-generated constructor stub
	}
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
	public int getNumberOfActionsAllowedForProtractors() {
		return numberOfActionsAllowedForProtractors;
	}
	public void setNumberOfActionsAllowedForProtractors(int numberOfActionsAllowedForProtractors) {
		this.numberOfActionsAllowedForProtractors = numberOfActionsAllowedForProtractors;
	}
	public int getNumberOfActionsAllowedForSupervisors() {
		return numberOfActionsAllowedForSupervisors;
	}
	public void setNumberOfActionsAllowedForSupervisors(int numberOfActionsAllowedForSupervisors) {
		this.numberOfActionsAllowedForSupervisors = numberOfActionsAllowedForSupervisors;
	}
	public int getNumberOfActionsAllowedForPreValidators() {
		return numberOfActionsAllowedForPreValidators;
	}
	public void setNumberOfActionsAllowedForPreValidators(int numberOfActionsAllowedForPreValidators) {
		this.numberOfActionsAllowedForPreValidators = numberOfActionsAllowedForPreValidators;
	}
	public int getNumberOfActionsAllowedForPresidents() {
		return numberOfActionsAllowedForPresidents;
	}
	public void setNumberOfActionsAllowedForPresidents(int numberOfActionsAllowedForPresidents) {
		this.numberOfActionsAllowedForPresidents = numberOfActionsAllowedForPresidents;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public Set<Classroom> getClassrooms() {
		return classrooms;
	}
	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	public Employee getDepartementHead() {
		return departementHead;
	}
	public void setDepartementHead(Employee departementHead) {
		this.departementHead = departementHead;
	}
	public University getUniversity() {
		return university;
	}
	public void setUniversity(University university) {
		this.university = university;
	}
	
}