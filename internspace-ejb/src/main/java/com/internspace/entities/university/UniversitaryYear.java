package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

// This could be set by the administrator of Internspace.
@Entity
@Table(name="universitary_year", uniqueConstraints = {@UniqueConstraint(columnNames={"start_date", "end_date"})})
public class UniversitaryYear implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="uy_id")
	long id;

	@Column(name = "start_date")
	Date startDate;
	@Column(name = "end_date")
	Date endDate;
	
	/*
	 * Associations
	 */
	
	@OneToMany(mappedBy = "universitaryYear")
	Set<StudyClass> studyClasses = new HashSet<>();
	
	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


}

