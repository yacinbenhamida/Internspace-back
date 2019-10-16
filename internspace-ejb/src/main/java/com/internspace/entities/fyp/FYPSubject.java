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
@Table(name="subject")
public class FYPSubject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="subject_id")
	long id;
	
	@Column(name="title")
	String title = "default";
	
	// Apply NLP with fypFile.features
	@Column(name="content")
	String content;
	
	/*
	 * Associations
	 */

	@ManyToMany(mappedBy = "subjects")
	Set<FYPCategory> categories;

	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<FYPCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<FYPCategory> categories) {
		this.categories = categories;
	}
}
