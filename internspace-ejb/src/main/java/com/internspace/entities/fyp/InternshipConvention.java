package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.Company;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;


@XmlRootElement
@Entity
@Table(name="internship_convention")
public class InternshipConvention implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ic_id")
	long id;

	String title;
	Date startDate;
	Date endDate;
	
	/*
	 * Associations
	 */
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	Company company;
	
	/*
	 * Getters & Setters
	 */
    
}
