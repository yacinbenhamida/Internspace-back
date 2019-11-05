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
	@Column(name="name")
	String name;
	@Column(name="address")
	String address;

	/*
	 * Associations
	 */
	
	@ManyToOne
	@JoinColumn(name = "uni_id")
	University university;
	
	@OneToMany(mappedBy = "site")
	Set<Departement> departements;
	
	// Explicitly check if this employee has InternshipDirector role.
	@OneToOne
	@JoinColumn(name = "internship_director_id")
	Employee internshipDirector;

	
	/*
	 * Getters & Setters
	 */
	
	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	@Override
	public String toString() {
		return "Site [address=" + address + ", departements=" + departements + ", id=" + id + ", internshipDirector="
				+ internshipDirector + ", name=" + name + ", University ID=" + university.getId() + "]";
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}
	
	
}