package com.internspace.entities.users;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.university.Departement;
import com.internspace.entities.university.Site;
import com.internspace.entities.university.University;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.internspace.entities.fyp.FYPDefense;
import com.internspace.entities.fyp.FYPIntervention;

@Entity
@Table(name = "employee")
public class Employee extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Role {
		internshipsDirector, departmentHead, teacher, admin,

	}

	@Column(name = "birth_date")
	Date birthDate;
	@Enumerated(EnumType.STRING)
	Role role;

	/*
	 * Associations
	 */

	// Only use this when role == internshipDirector
	@OneToOne(mappedBy = "internshipDirector")
	Site site;

	// Has a no of interventions if the employee is a teacher
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	Set<FYPIntervention> interventions;
	// use this when role == teacher
	@ManyToOne(optional = true)
	@JoinColumn(name = "department_id")
	Departement department;
	
	

	/*
	 * Getters & Setters
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setInterventions(Set<FYPIntervention> interventions) {
		this.interventions = interventions;
	}

	public Departement getDepartment() {
		return department;
	}

	public void setDepartment(Departement department) {
		this.department = department;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
