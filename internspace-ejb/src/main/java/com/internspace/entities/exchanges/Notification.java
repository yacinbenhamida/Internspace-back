package com.internspace.entities.exchanges;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;
import com.internspace.entities.users.User;
@Entity(name="Notification")
public class Notification implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String content;
	
	boolean seen;
	
	@ManyToOne
	@JoinColumn(name="sender_id")
	User sender;
	
	@ManyToOne
	@JoinColumn(name="reciver_id")
	User reciever;
	@Column(name="date_of_emssion")
	LocalDateTime dateOfEmission;

	public Notification() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	
	
	public LocalDateTime getDateOfEmission() {
		return dateOfEmission;
	}

	public void setDateOfEmission(LocalDateTime dateOfEmission) {
		this.dateOfEmission = dateOfEmission;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReciever() {
		return reciever;
	}

	public void setReciever(User reciever) {
		this.reciever = reciever;
	}


	
	
}
