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
@Table(name = "student_quiz")
public class StudentQuiz implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_quiz_id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "user_id", insertable = true, updatable = true)
	private Student student;
	@ManyToOne
	@JoinColumn(name = "quiz_id", referencedColumnName = "quiz_id", insertable = true, updatable = true)
	private Quiz quiz;
	private int score;

	@Column(name = "curr_question_index")
	private int currentQuestionIndex;

	public StudentQuiz() {

	}

	public StudentQuiz(long id, Student student, Quiz quiz, int score) {
		super();
		this.id = id;
		this.student = student;
		this.quiz = quiz;
		this.score = score;
	}

	public StudentQuiz(Student student, Quiz quiz, int score) {
		super();
		this.student = student;
		this.quiz = quiz;
		this.score = score;
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

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}

	public void setCurrentQuestionIndex(int currentQuestionIndex) {
		this.currentQuestionIndex = currentQuestionIndex;
	}
}
