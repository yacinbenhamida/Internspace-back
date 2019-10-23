package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Table;

import com.internspace.entities.users.Company;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name="fyp_subject")
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
	@Column(name="content", length = 65535, columnDefinition = "text")
	String content;
	
	@Column(name="max_applicants")
	int maxApplicants;
	
	@Column(name="curr_applicants_count")
	int curApplicantsCount;
	
	/*
	 * Associations
	 */
	
	@OneToOne
	@JoinColumn(name = "fyp_file_id")
	FYPFile fypFile; // NULL ? mazel famech chkon 9a3d yaaml f PFE mte3o lehné

	@ManyToOne
	@JoinColumn(name = "company_id")
	Company company;
	
	// Many to Many to Subjects using custom association table.
	@OneToMany(mappedBy="subject", fetch = FetchType.LAZY)
	Set<StudentFYPSubject> studentSubjects;
	
	/*
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	Set<Internship> internships;
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

}
