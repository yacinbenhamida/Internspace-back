package com.internspace.entities.fyp;

import com.internspace.entities.users.Company;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.Student;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@XmlRootElement
@Entity
@Table(name="internship")
public class Internship implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="internship_id")
	long id;

	String title;
	
	// Compare with University's location to get abroad internships.
	String location; 
	
	/*
	 * Associations
	 */
	
	@OneToOne
	FYPFile fypFile;
	
	@OneToOne(mappedBy = "internship")
	Student student;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	Company company;
	
	@ManyToOne
	@JoinColumn(name = "subject_id")
	FYPSubject subject;
	
	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public FYPFile getFypFile() {
		return fypFile;
	}

	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public FYPSubject getSubject() {
		return subject;
	}

	public void setSubject(FYPSubject subject) {
		this.subject = subject;
	}

	
	
    
	
}
