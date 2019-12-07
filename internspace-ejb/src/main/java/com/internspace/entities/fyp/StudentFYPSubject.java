package com.internspace.entities.fyp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.internspace.entities.users.Student;

@Entity
@Table(name="student_fyp_subject")
public class StudentFYPSubject implements Serializable{

	@Transient
	public static final String defaultReason = "Your profile didn\'t match the subject\'s position requirements, we got your profile for future opportunities.";
	
	public enum ApplianceStatus {
		none, // When there isn't a row matching student and subject
		pending,
		refused,
		accepted,
	}
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes / Associations
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_fyp_subject_id")
	long id;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	ApplianceStatus applianceStatus;
	
	@ManyToOne
	@JoinColumn(name="student_id", nullable = false, referencedColumnName="user_id", insertable = true, updatable = false)
	Student student;
	
	@ManyToOne
	@JoinColumn(name="subject_id", nullable = false, referencedColumnName="subject_id", insertable = true, updatable = false)
	FYPSubject subject;

	// To be shown to the student as a reason of the appliance refusal
	@Column(name = "refusal_reason", columnDefinition = "varchar(255) default '" + "Your profile didn\\'t match the subject\\'s position requirements, we got your profile for future opportunities." + "'")
	String refusalReason;
	
	/*
	 * Construction
	 */
	
	public StudentFYPSubject()
	{
		
	}

	public StudentFYPSubject(Student student, FYPSubject subject)
	{
		this.student = student;
		this.subject = subject;
	}
	
	/*
	 * Getters & Setters
	 */
	
	public Student getStudent()
	{
		return this.student;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ApplianceStatus getApplianceStatus() {
		return applianceStatus;
	}

	public void setApplianceStatus(ApplianceStatus applianceStatus) {
		this.applianceStatus = applianceStatus;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}
	
}
