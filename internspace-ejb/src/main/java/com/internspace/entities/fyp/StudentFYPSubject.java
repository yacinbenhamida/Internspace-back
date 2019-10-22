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

import com.internspace.entities.users.Student;

@Entity
@Table(name="student_fyp_subject")
public class StudentFYPSubject implements Serializable{

	public enum ApplianceStatus {
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
	@JoinColumn(name="student_id", referencedColumnName="user_id", insertable = false, updatable = false)
	Student student;
	
	@ManyToOne
	@JoinColumn(name="subject_id", referencedColumnName="subject_id", insertable = false, updatable = false)
	FYPSubject subject;

}
