package com.internspace.entities.fyp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.Employee;

@XmlRootElement
@Entity
@Table(name = "InternShipPreference")
public class InternShipPreference {
private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="intpref_id")
	long id;


	
	/*
	 * Associations
	 */
	
	@ManyToOne
	@JoinColumn(name = "teacher_id")
	Employee teacher;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	FYPCategory category;
	
	
	/*
	 * getters setters
	 */
	
public FYPCategory getCategory() {
	return category;
}
public void setTeacher(Employee teacher) {
	this.teacher = teacher;
}public void setCategory(FYPCategory category) {
	this.category = category;
}public Employee getTeacher() {
	return teacher;
}public long getId() {
	return id;
}public void setId(long id) {
	this.id = id;
}
}
