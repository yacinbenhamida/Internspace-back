package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="university")
public class University implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="university_id")
	long id;
	@Column(name="name")
	String name;
	@Temporal (TemporalType.DATE)
	Date openingYear;
	@Column(name="owner")
	String owner;
	@Column(name="logoUrl")
	String logoUrl;
	
	/*
	 * Associations
	 */
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="university")
	private Set<Site> sites;
	
	@OneToMany(mappedBy = "university")
	Set<Departement> departements;

	
	
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

	public Date getOpeningYear() {
		return openingYear;
	}

	public void setOpeningYear(Date openingYear) {
		this.openingYear = openingYear;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		University other = (University) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}

