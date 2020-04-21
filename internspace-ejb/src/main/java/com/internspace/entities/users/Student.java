package com.internspace.entities.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.fyp.StudentCategoryPreference;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.university.StudyClass;

import com.internspace.entities.university.University;

import com.internspace.entities.users.Employee.Role;


import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "student")
public class Student extends User {

	private static final long serialVersionUID = 1L;
	
	public enum Role {
		Student

	}

	@Column(name = "birth_date")
	String birthDate;
	
	@Enumerated(EnumType.STRING)
	Role role;


	// The student isn't allowed to have a reporter without initially submitting a
	// paper report to the administration
	@Column(name = "has_submitted_a_report")
	boolean hasSubmittedAReport;

	@Column(name="cin")
	String cin;
	
	@Column(name="pass_generated")
	String passGenerated;
	
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
	

	/*
	 * Associations
	 */


	@OneToOne
	@JoinColumn(name = "fyp_file_id")
	FYPFile fypFile;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "study_class_id")
	StudyClass studyClass;
	// Many to Many to Categories using custom association table.
	@OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
	Set<StudentCategoryPreference> preferedCategories;

	// Many to Many to Subjects using custom association table.
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	Set<StudentFYPSubject> studentSubjects;
	
	/*@OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
	InternshipConvention internshipConvention;*/
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Getters & Setters
	 */
	  public String getPassword() { return password; }
	  
	  public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setPassword(String password) { this.password = password; }
	  
	  public String getUsername() { return username; }
	  
	  public void setUsername(String username) { this.username = username; }
	  
	  //public Date getBirthDate() { return birthDate; }
	  
	  // public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
	  
	  public boolean isHasSubmittedAreport() { return hasSubmittedAReport; }
	  
	
	
	public Boolean getIsSaved() {
		return isSaved;
	}

	

	public String getPassGenerated() {
		return passGenerated;
	}

	public void setPassGenerated(String passGenerated) {
		this.passGenerated = passGenerated;
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

	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
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
	public void setPreferedCategories(Set<StudentCategoryPreference> preferedCategories) {
		this.preferedCategories = preferedCategories;
	}
	 */
	
	public Set<StudentCategoryPreference> getPreferedCategories() {
		return this.preferedCategories;
	}

	public void setPreferedCategories(Set<StudentCategoryPreference> preferedCategories) {
		this.preferedCategories = preferedCategories;
	}

	public FYPFile getFypFile()
	{
		return this.fypFile;
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

	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

	



	
	

	
	
	
}
	
	

