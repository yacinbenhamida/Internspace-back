package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Date;
import com.internspace.entities.users.Company;
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

// Student creates this by form
// If FYPFile has data, we fullfil the Convention with it
@Entity
@Table(name = "internship_convention")
public class InternshipConvention implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ic_id")
	long id;

	@Column(name = "start_date")
	Date startDate;
	@Column(name = "end_date")
	Date endDate;
	@Column(name = "canceled", columnDefinition = "boolean default false")
	boolean isCanceled;

	/*
	 * (Date début, Date fin, entreprise) Entreprise : siteweb, adresse, pays, nom
	 * prénom responsable, email responsable, email encadrant entreprise, tel.
	 */
	@Column(name = "company_supervisor_email")
	String companySupervisorEmail;

	/*
	 * Associations
	 */

	@ManyToOne
	@JoinColumn(name = "company_id")
	Company company;

	@OneToOne
	@JoinColumn(name = "student_id")
	Student student;

	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getCompanySupervisorEmail() {
		return companySupervisorEmail;
	}

	public void setCompanySupervisorEmail(String companySupervisorEmail) {
		this.companySupervisorEmail = companySupervisorEmail;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
	
	

}
