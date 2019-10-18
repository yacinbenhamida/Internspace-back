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
	@Column(name = "protractors_no_of_actions_allowed")
	int numberOfActionsAllowedForProtractors; //this is edited by the internships director
	@Column(name = "supervisors_no_of_actions_allowed")
	int numberOfActionsAllowedForSupervisors; //this is edited by the internships director
	@Column(name = "prevalidatiors_no_of_actions_allowed")
	int numberOfActionsAllowedForPreValidators; //this is edited by the internships director
	@Column(name = "president_no_of_actions_allowed")
	int numberOfActionsAllowedForPresidents; //this is edited by the internships director
	
	/*
	 * Associations
	 */
	
	@ManyToOne
	University university;
	
	@OneToMany(mappedBy = "departement")
	Set<Site> sites;
	@OneToOne
	
	// Explicitly check if this employee has DepartmentDirector role.
	Employee director;
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
	public University getUniversity() {
		return university;
	}
	public void setUniversity(University university) {
		this.university = university;
	}
	public Set<Site> getSites() {
		return sites;
	}
	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}
	public Employee getDirector() {
		return director;
	}
	public void setDirector(Employee director) {
		this.director = director;
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
	
	
}