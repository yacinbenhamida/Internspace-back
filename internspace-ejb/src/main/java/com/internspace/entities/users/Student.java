package com.internspace.entities.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.fyp.Internship;
import com.internspace.entities.university.Classroom;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;


import com.internspace.entities.exchanges.Notification;

@Entity
@Table(name="student")
public class Student extends User {

	private static final long serialVersionUID = 1L;

	String password;
	String username;
	Date birthDate;
	int classYear; // Values like (1, 2, 3, 4, 5, ...), 5 means 5éme année...
	
	/*
	 * Associations
	 */

	@OneToOne
	Internship internship;
	
	@ManyToOne
	Classroom classroom;
	@OneToMany(mappedBy="student", fetch = FetchType.EAGER)
	List<Notification> notifications;

	@Column(columnDefinition = "boolean default false")
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
