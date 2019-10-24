package com.internspace.entities.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.StudentCategoryPreference;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.university.StudyClass;

import java.util.Date;
import java.util.Set;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "student")
public class Student extends User {

	private static final long serialVersionUID = 1L;

	@Column(name = "birth_date")
	Date birthDate;

	// The student isn't allowed to have a reporter without initially submitting a
	// paper report to the administration
	@Column(name = "has_submitted_a_report")
	boolean hasSubmittedAReport;
	

	@Column(name="cin")
	String cin;

	
	/*
	 * Associations
	 */

	/*
	 * @OneToOne
	 * 
	 * @JoinColumn(name = "internship_id") Internship internship;
	 */

	@OneToOne
	@JoinColumn(name = "fyp_file_id")
	FYPFile fypFile;

	@ManyToOne
	@JoinColumn(name = "study_class_id")
	StudyClass studyClass;

	// Many to Many to Categories using custom association table.
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	Set<StudentCategoryPreference> preferedCategories;

	// Many to Many to Subjects using custom association table.
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	Set<StudentFYPSubject> studentSubjects;

	@Column(name = "is_created", columnDefinition = "boolean default false")
	Boolean isCreated;

	// l etudiant est enregistrée
	@Column(name = "is_saved", columnDefinition = "boolean default false")
	Boolean isSaved;

	// l etudiant est autorisée a passer son PFE

	@Column(name = "is_autorised ", columnDefinition = "boolean default false")
	Boolean isAutorised;

	// behs nshouf est ce qui letudiant ynajem y3adi pfe ou nn ( ynajem yconnecti
	// fel platforme ou nn )
	@Column(name = "is_disabled", columnDefinition = "boolean default false")
	Boolean isDisabled;

	public Student() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Getters & Setters
	 */
	/*
	 * public String getPassword() { return password; }
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 * 
	 * public String getUsername() { return username; }
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * public Date getBirthDate() { return birthDate; }
	 * 
	 * public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
	 * 
	 * public boolean isHasSubmittedAreport() { return hasSubmittedAReport; }
	 * 
	 */
	public Boolean getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(Boolean isSaved) {
		this.isSaved = isSaved;
	}

	public Boolean getIsAutorised() {
		return isAutorised;
	}

	public void setIsAutorised(Boolean isAutorised) {
		this.isAutorised = isAutorised;
	}


	
	
	
	public void setHasSubmittedAreport(boolean hasSubmittedAreport) {
		this.hasSubmittedAReport = hasSubmittedAreport;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public StudyClass getStudyClass() {
		return studyClass;
	}
	public void setStudyClass(StudyClass studyClass) {
		this.studyClass = studyClass;
	}
	public Boolean getIsCreated() {
		return isCreated;
	}

	public void setIsCreated(Boolean isCreated) {
		this.isCreated = isCreated;
	}

	public boolean isHasSubmittedAReport() {
		return hasSubmittedAReport;
	}

	public void setHasSubmittedAReport(boolean hasSubmittedAReport) {
		this.hasSubmittedAReport = hasSubmittedAReport;
	}
	/*
	 * 
	 * public void setStudyClass(StudyClass studyClass) { this.studyClass =
	 * studyClass; }
	 * 
	 * /*public Set<StudentCategoryPreference> getPreferedCategories() { return
	 * preferedCategories; }
	 */

	public void setPreferedCategories(Set<StudentCategoryPreference> preferedCategories) {
		this.preferedCategories = preferedCategories;
	}

	/*
	 * public Set<StudentFYPSubject> getStudentSubjects() { return studentSubjects;
	 * }
	 */

	public void setStudentSubjects(Set<StudentFYPSubject> studentSubjects) {
		this.studentSubjects = studentSubjects;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}
	
	
	
}
	
	

