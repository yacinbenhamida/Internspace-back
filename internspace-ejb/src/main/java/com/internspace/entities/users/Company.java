package com.internspace.entities.users;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.InternshipConvention;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company extends User {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	// ... Email, last-name and first-name for responsible are the User's (from Parent)
	@Column(unique = true)
	String name;
	String website;
	String address;
	String country;
	//verify if the company is real or not
	@Column(name = "is_real", columnDefinition = "boolean default false")
	Boolean isReal;
	// This is the principal supervisor email.
	// This will be used by InternshipConvention if not inserted in there.
	// Can ignore this.
	@Column(name = "supervisor_email")
	String supervisorEmail; 
	@Column(name = "phone_number")
	String phoneNumber;
	
	String logoUrl;
	String slogan;
	String description;
	
	/*
	 * Associations
	 */

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	Set<FYPSubject> subjects;

	@OneToMany(mappedBy = "company")
	Set<InternshipConvention> internshipConventions;// = new HashSet<InternshipConvention>();


	/*
	 * Construction
	 */
	
	public Company()
	{
		
	}

	/*
	 * Getters & Setters
	 */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSubjects(Set<FYPSubject> subjects) {
		this.subjects = subjects;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogoUrl()
	{
		return logoUrl;
	}
	
	public String getSlogan()
	{
		return slogan;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setInternshipConventions(Set<InternshipConvention> internshipConventions) {
		this.internshipConventions = internshipConventions;
	}

	public Boolean getIsReal() {
		return isReal;
	}

	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}
	
	

}
