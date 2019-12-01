package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Table;

import com.internspace.entities.university.Departement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "category")
public class FYPCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	long id;

	@Column(name = "name")
	String name = "default";
	@Column(name = "is_approved") // Teachers can suggest categories.
	boolean isApproved = false;
	String description;
	/*
	 * Associations
	 */

	// Many to Many to Students using custom association table.
	@OneToMany(mappedBy = "category")
	Set<StudentCategoryPreference> preferedByStudents;

	@ManyToMany
	@JoinTable(name = "fypfiles_categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "fypfile_id"))
	Set<FYPFile> fypFiles;

	@ManyToMany
	@JoinTable(name = "subjects_categories", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	Set<FYPSubject> subjects;
	@ManyToOne
	@JoinColumn(name = "department_id")
	Departement department;
	/*
	 * Getters & Setters
	 */

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

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	
	public Departement getDepartment() {
		return department;
	}

	public void setDepartment(Departement department) {
		this.department = department;
	}

	public void setSubjects(Set<FYPSubject> subjects) {
		this.subjects = subjects;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
