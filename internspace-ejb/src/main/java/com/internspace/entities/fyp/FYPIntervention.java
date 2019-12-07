package com.internspace.entities.fyp;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.Fetch;

import com.internspace.entities.users.Employee;

// This class specifies a teacher's role towards an FYP File
@Entity(name = "fyp_intervention")
public class FYPIntervention implements Serializable {
	public enum TeacherRole {
		reporter, supervisor, preValidator, juryPresident,
	}

	/**
	 * Attributes
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(name = "assignment_date")
	LocalDate assignmentDate;

	@Enumerated(EnumType.STRING)
	TeacherRole teacherRole;

	@Column(name = "given_mark")
	int givenMark;
	@Column(name = "actions_remaining")
	int actionsRemaining;
	
	/*
	 * Associations
	 */

	// Explicitly check if this employee has Teacher role.
	@ManyToOne
	Employee teacher;
	@ManyToOne(cascade = CascadeType.ALL)
	FYPFile fypFile;
	

	public FYPIntervention() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(LocalDate assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public TeacherRole getTeacherRole() {
		return teacherRole;
	}

	public void setTeacherRole(TeacherRole teacherRole) {
		this.teacherRole = teacherRole;
	}

	public Employee getTeacher() {
		return teacher;
	}

	public void setTeacher(Employee teacher) {
		this.teacher = teacher;
	}

	public FYPFile getFypFile() {
		return fypFile;
	}

	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

	public int getGivenMark() {
		return givenMark;
	}

	public void setGivenMark(int givenMark) {
		this.givenMark = givenMark;
	}

	public int getActionsRemaining() {
		return actionsRemaining;
	}

	public void setActionsRemaining(int actionsRemaining) {
		this.actionsRemaining = actionsRemaining;
	}

	@Override
	public String toString() {
		return "FYPIntervention [id=" + id + ", assignmentDate=" + assignmentDate + ", teacherRole=" + teacherRole
				+ ", givenMark=" + givenMark + ", actionsRemaining=" + actionsRemaining + ", teacher=" + teacher
				+ ", fypFile=" + fypFile + "]";
	}

}
