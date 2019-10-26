package com.internspace.entities.fyp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

// @Entity
// @Table(name = "internship")
@Deprecated
public class Internship implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "internship_id")
	long id;

	@OneToOne
	@JoinColumn(name = "fyp_file_id")
	FYPFile fypFile;

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

	/*public FYPFile getFypFile() {
		return fypFile;
	}*/

	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

	/*
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	 */
	
	/*
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	*/
	public FYPSubject getSubject() {
		return subject;
	}

	public void setSubject(FYPSubject subject) {
		this.subject = subject;
	}

}
