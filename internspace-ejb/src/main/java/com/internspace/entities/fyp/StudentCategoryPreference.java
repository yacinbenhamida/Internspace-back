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

import com.internspace.entities.users.Student;

@Entity
@Table(name="student_category_preference")
public class StudentCategoryPreference implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes / Associations
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="scp_id")
	long id;
	
	@Column(name = "skill_score") // MAX == 10
	int skillScore;
	
	@ManyToOne
	@JoinColumn(name="student_id", referencedColumnName="user_id", insertable = false, updatable = false)
	Student student;
	
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="category_id", insertable = false, updatable = false)
	FYPCategory category;

}
