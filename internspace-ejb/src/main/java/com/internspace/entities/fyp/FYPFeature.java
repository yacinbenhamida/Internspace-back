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
@Table(name="feature")
public class FYPFeature implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="feature_id")
	long id;
	
	@Column(name="content")
	String content = "default";
	
	/*
	 * Associations
	 */

	@ManyToMany
	Set<FYPFile> fypFiles;
	
	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<FYPFile> getFypFiles() {
		return fypFiles;
	}

	public void setFypFiles(Set<FYPFile> fypFiles) {
		this.fypFiles = fypFiles;
	}
}
