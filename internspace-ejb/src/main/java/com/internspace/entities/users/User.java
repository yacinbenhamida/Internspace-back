package com.internspace.entities.users;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Table;

import com.internspace.entities.exchanges.Notification;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
// To ensure TPC (type-per-class)
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
	public enum UserType{
		superAdmin,employee,company,student
	}
	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	protected long id;

	@Column(name = "first_name")
	protected String firstName;
	@Column(name = "last_name")
	protected String lastName;
	protected String email;
	protected String username;
	protected String password;
	protected String pictureUrl; // You can use this for profile picture or school/company logo
	@Column(name="user_type")
	@Enumerated(EnumType.STRING)
	protected UserType userType; 
	/*
	 * Associations
	 */

	// TODO: Add user-relevant associations.

	/*
	 * Construction
	 */

	public User()
	{
		
	}
	
	public User(String firstName, String lastName, String email, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
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

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", username=" + username + ", password=" + password + ", userType=" + userType + "]";
	}
	
}
