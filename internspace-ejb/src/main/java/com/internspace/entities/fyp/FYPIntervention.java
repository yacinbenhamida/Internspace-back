package com.internspace.entities.fyp;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.internspace.entities.users.Employee;


// this class specifies a teacher's role towards an internship sheet
@Entity(name = "FYP_INTERVENTION")
public class FYPIntervention implements Serializable{
	public enum TeacherRole{
		protractor,
		supervisor,
		preValidator,
		juryPresident,
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	LocalDate assignmentDate;
	@Enumerated(EnumType.STRING)
	TeacherRole teacherRole;
	@ManyToOne
	Employee  teacher;
	@ManyToOne
	FYPFile internshipSheet;
	@Column(name="given_mark")
	int givenMark;
	
	public FYPIntervention() {
		// TODO Auto-generated constructor stub
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
	public FYPFile getInternshipSheet() {
		return internshipSheet;
	}
	public void setInternshipSheet(FYPFile internshipSheet) {
		this.internshipSheet = internshipSheet;
	}
	public int getGivenMark() {
		return givenMark;
	}
	public void setGivenMark(int givenMark) {
		this.givenMark = givenMark;
	}
	
}
