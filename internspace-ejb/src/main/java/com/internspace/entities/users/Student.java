package com.internspace.entities.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.fyp.Internship;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.university.StudyClass;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.OneToMany;


import com.internspace.entities.exchanges.Notification;

@Entity
@Table(name="student")
public class Student extends User {

	private static final long serialVersionUID = 1L;

	String password;
	String username;
	Date birthDate;

	/*
	 * Associations
	 */

	@OneToOne
	@JoinColumn(name = "internship_id")
	Internship internship;

	@ManyToOne
	@JoinColumn(name = "study_class_id")
	StudyClass studyClass;
	
	
	@OneToMany(mappedBy="student", fetch = FetchType.EAGER)
	Set<Notification> notifications;
	
	// Many to Many to Subjects using custom association table.
	@OneToMany(mappedBy="student", fetch = FetchType.EAGER)
	Set<StudentFYPSubject> studentSubjects;
	
	@Column(name = "is_created", columnDefinition = "boolean default false")
	Boolean isCreated ;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * Getters & Setters
	 */

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
}

