package com.internspace.entities.fyp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.internspace.entities.users.Student;


@Entity
@Table(name="student")
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
	@Column(name="subject_id")
	long id;
	
	@Column(name = "status")
	ApplianceStatus applianceStatus;
	
	@ManyToOne
	@JoinColumn(name="student_id", referencedColumnName="user_id", insertable = false, updatable = false)
	Student student;
	
	@ManyToOne
	@JoinColumn(name="subject_id", referencedColumnName="subject_id", insertable = false, updatable = false)
	FYPSubject subject;

}
