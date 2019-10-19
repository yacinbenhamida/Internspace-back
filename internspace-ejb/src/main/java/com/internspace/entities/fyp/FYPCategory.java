package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name="category")
public class FYPCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id")
	long id;
	
	@Column(name="name")
	String name = "default";
	@Column(name="is_approved") // a teacher can suggest categories
	boolean isApproved = false;
	/*
	 * Associations
	 */

	@ManyToMany(mappedBy = "categories")
	Set<FYPFile> fypFiles;

	@ManyToMany
	Set<FYPSubject> subjects;
	
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

	public Set<FYPFile> getFypFiles() {
		return fypFiles;
	}

	public void setFypFiles(Set<FYPFile> fypFiles) {
		this.fypFiles = fypFiles;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Set<FYPSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<FYPSubject> subjects) {
		this.subjects = subjects;
	}
	
	
}
