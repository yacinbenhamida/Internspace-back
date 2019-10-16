package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

/*
 * La fiche PFE (Titre, description, problématique, fonctionnalités, catégorie, mots clé, entreprise). 
 * Exemple de catégorie : .NET, nodeJS, devops, JavaEE. 
 * Une fiche PFE peut appartenir à plusieurs catégories. 
 */

@XmlRootElement
@Entity
@Table(name="fyp_file")
public class FYPFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fyp_file_id")
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
