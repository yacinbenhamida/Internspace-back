package com.internspace.entities.fyp.quiz;

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
@Table(name = "student_quiz_response")
public class StudentQuizResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sqr_id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = true, updatable = true)
	private Student student;
	@ManyToOne
	@JoinColumn(name = "response_id", referencedColumnName = "response_id", insertable = true, updatable = true)
	private QuestionResponse response;
	private boolean isChecked;

	public StudentQuizResponse() {
	}

	public StudentQuizResponse(int id, Student student, QuestionResponse response, boolean isChecked) {
		super();
		this.id = id;
		this.student = student;
		this.response = response;
		this.isChecked = isChecked;
	}

	public StudentQuizResponse(Student student, QuestionResponse response, boolean isChecked) {
		super();
		this.student = student;
		this.response = response;
		this.isChecked = isChecked;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public QuestionResponse getResponse() {
		return response;
	}

	public void setResponse(QuestionResponse response) {
		this.response = response;
	}

	public boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
