package com.internspace.entities.fyp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.internspace.entities.users.Employee;

@Entity
@Table(name = "internship_preference")
public class InternshipPreference implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "int_pref_id")
	long id;

	/*
	 * Associations
	 */

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	Employee teacher; // Explicitly check if this employee has Teacher role.

	@ManyToOne
	@JoinColumn(name = "category_id")
	FYPCategory category;

	/*
	 * Getters & Setters
	 */

	public FYPCategory getCategory() {
		return category;
	}

	public void setTeacher(Employee teacher) {
		this.teacher = teacher;
	}

	public void setCategory(FYPCategory category) {
		this.category = category;
	}

	public Employee getTeacher() {
		return teacher;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
