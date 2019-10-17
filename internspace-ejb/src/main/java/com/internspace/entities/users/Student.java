package com.internspace.entities.users;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.internspace.entities.exchanges.Notification;

@Entity
@Table(name="student")
public class Student extends User {

	private static final long serialVersionUID = 1L;
	
	String password;
	String username;
	Date birthDate;
	@OneToMany(mappedBy="student")
	List<Notification> notifications;
	
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

