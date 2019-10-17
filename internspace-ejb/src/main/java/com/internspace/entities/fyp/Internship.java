package com.internspace.entities.fyp;

import com.internspace.entities.users.Company;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.Student;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	
	@OneToOne(mappedBy = "internship")
	FYPFile fypFile;
	
	@OneToOne(mappedBy = "internship")
	Student student;
	
	@ManyToOne
	Company company;
	
	@ManyToOne
	FYPSubject subject;

	/*
	 * Getters & Setters
	 */
    
	
}
