package com.internspace.entities.university;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.internspace.entities.fyp.FYPFile;

// This could be set by the administrator of Internspace.
@Entity
@Table(name = "universitary_year", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "start_date", "end_date" }) })
public class UniversitaryYear implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uy_id")
	long id;

	@Column(name = "start_date")
	int startDate;
	@Column(name = "end_date")
	int endDate;
	
	/*
	 * Associations
	 */

	@OneToMany(mappedBy = "universitaryYear", fetch = FetchType.LAZY)
	Set<FYPFile> fypFiles = new HashSet<>();

	@OneToMany(mappedBy = "universitaryYear")
	Set<StudyClass> studyClasses = new HashSet<>();

	@OneToMany(mappedBy = "currentUniversitaryYear", fetch = FetchType.LAZY)
	Set<University> universities = new HashSet<>();

	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	/*
	public Set<StudyClass> getStudyClasses() {
		return studyClasses;
	}

	public void setStudyClasses(Set<StudyClass> studyClasses) {
		this.studyClasses = studyClasses;
	}
*/
}
