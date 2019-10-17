package com.internspace.entities.users;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.university.Site;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.internspace.entities.exchanges.Notification;

@Entity
@Table(name="employee")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Employee extends User implements Serializable{

	private static final long serialVersionUID = 1L;
	public enum Role{
		internshipDirector,
		departmentHead,
		teacher,
		admin,
		superAdmin,
		
	}
	
	String password;
	String username;
	Date birthDate;
	Role role;
	
	/*
	 * Associations
	 */
	
	// Only use this when role == internshipDirector
	@OneToOne(mappedBy = "internshipDirector")
	Site site;
	
	@OneToMany(mappedBy="employee")
	List<Notification> notifications;
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

