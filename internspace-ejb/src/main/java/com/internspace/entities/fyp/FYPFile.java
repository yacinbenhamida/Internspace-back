package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.internspace.entities.university.UniversitaryYear;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

/*
 * La fiche PFE (Titre, description, problématique, fonctionnalités, catégorie, mots clé, entreprise). 
 * Exemple de catégorie : .NET, nodeJS, devops, JavaEE. 
 * Une fiche PFE peut appartenir à plusieurs catégories. 
 */

@Entity
@Table(name="fyp_file")
public class FYPFile implements Serializable {

	public enum FYPFileStatus {
		pending,			// Still waiting for InternshipDirector to confirm/decline.
		confirmed,			// InternshipDirector confirmed this.
		declined			// InternshipDirector declined this.
	}
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fyp_file_id")
	long id;
	
	String title;
	String description;
	String problematic;
	@ManyToOne
	@JoinColumn(name = "uni_year")
	UniversitaryYear universitaryYear;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	FYPFileStatus fileStatus;
	
	@Column(name="canceled" ,columnDefinition = "boolean default false")
	Boolean isCanceled;
	@Column(name="final_mark")
	int finalMark;
	
	/*
	 * Associations
	 */


	@ManyToMany(mappedBy = "fypFiles")
	Set<FYPFeature> features;
	
	@ManyToMany(mappedBy = "fypFiles")
	Set<FYPKeyword> keywords;

	@OneToOne(mappedBy = "fypFile")
	Internship internship;
	
	@OneToMany(mappedBy="internshipSheet")
	List<FYPIntervention> interventions;
	
	@ManyToMany(mappedBy = "fypFiles")
	Set<FYPCategory> categories;

	/*
	 * Getters & Setters
	 */
    
	public Set<FYPCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<FYPCategory> categories) {
		this.categories = categories;
	}

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

	public List<FYPIntervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<FYPIntervention> interventions) {
		this.interventions = interventions;
	}

	public Internship getInternship() {
		return internship;
	}

	public void setInternship(Internship internship) {
		this.internship = internship;
	}

	public UniversitaryYear getUniversitaryYear() {
		return universitaryYear;
	}

	public void setUniversitaryYear(UniversitaryYear universitaryYear) {
		this.universitaryYear = universitaryYear;
	}

	public FYPFileStatus getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(FYPFileStatus fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Boolean getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(Boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public int getFinalMark() {
		return finalMark;
	}

	public void setFinalMark(int finalMark) {
		this.finalMark = finalMark;
	}
	
	
}
