package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.Company;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;


@XmlRootElement
@Entity
@Table(name="internship_convention")
public class InternshipConvention implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ic_id")
	long id;
	
	@Column(name="title")
	String title;
	@Column(name="description")
	String description;
	@Column(name="problematic")
	String problematic;
	
	/*
	 * Associations
	 */
	
	@ManyToMany
	Set<FYPCategory> categories;

	@ManyToMany
	Set<FYPFeature> features;
	
	@ManyToMany
	Set<FYPKeyword> keywords;

	@OneToOne
	Internship internship;
	
	@ManyToOne
	Company company;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProblematic() {
		return problematic;
	}

	public void setProblematic(String problematic) {
		this.problematic = problematic;
	}

	public Set<FYPCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<FYPCategory> categories) {
		this.categories = categories;
	}

	public Set<FYPFeature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<FYPFeature> features) {
		this.features = features;
	}

	public Set<FYPKeyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<FYPKeyword> keywords) {
		this.keywords = keywords;
	}
}
