package com.internspace.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	public enum Role{
		departmentDirector,
		teacher,
		admin,
		superAdmin,
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	long id;
	
	@Column(name="FIRST_NAME")
	String firstName;
	@Column(name="LAST_NAME")
	String lastName;
	@Column(name="EMAIL")
	String email;
	
	String password;
	String username;
	Date birthDate;
	
	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNom() {
		return firstName;
	}
	public void setNom(String nom) {
		this.firstName = nom;
	}
	public String getPrenom() {
		return lastName;
	}
	public void setPrenom(String prenom) {
		this.lastName = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	

}

