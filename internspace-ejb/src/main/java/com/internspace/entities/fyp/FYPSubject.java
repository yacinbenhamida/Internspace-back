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
@Table(name = "fyp_subject")
public class FYPSubject implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subject_id")
	long id;

	@Column(name = "title")
	String title = "default";

	// Apply NLP with fypFile.features
	@Column(name = "content", length = 65535, columnDefinition = "text")
	String content;

	@Column(name = "max_applicants", columnDefinition = "int default 1")
	int maxApplicants;

	// This won't be the best option because
	// When we update max applicants, we might want to change
	// Cur applicants count, which would lead to
	// Conspiracy with ground-truth
	/*
	@Column(name = "curr_applicants_count", columnDefinition = "int default 1")
	int curApplicantsCount;
	 */
	
	/*
	 * Associations
	 */

	@OneToOne(mappedBy = "subject")
	FYPFile fypFile; // NULL ? mazel famech chkon 9a3d yaaml f PFE mte3o lehné

	@ManyToOne
	@JoinColumn(name = "company_id")
	Company company;

	// Many to Many to Subjects using custom association table.
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	Set<StudentFYPSubject> studentSubjects;

	@ManyToMany(mappedBy = "subjects")
	Set<FYPCategory> categories;

	/*
	 * Construction
	 */

	public FYPSubject() {

	}

	public FYPSubject(Company company, FYPFile fypFile, String title, String content, int maxApplicants) {
		this.company = company;
		this.fypFile = fypFile;
		this.title = title;
		this.content = content;
		this.maxApplicants = maxApplicants;
	}

	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getMaxApplicants() {
		return maxApplicants;
	}

	public void setMaxApplicants(int maxApplicants) {
		this.maxApplicants = maxApplicants;
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

	/*
	 * public FYPFile getFypFile() { return fypFile; }
	 */
	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

}
