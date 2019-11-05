package com.internspace.entities.fyp.quiz;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quiz_question")
public class QuizQuestion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_question_id")
	private int id;
	private String content;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "quiz", insertable = true, updatable = true)
	private Quiz quiz;

	@OneToMany(mappedBy = "question", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<QuestionResponse> responses;

	public QuizQuestion() {
	}

	public QuizQuestion(int id, String content, List<QuestionResponse> responses) {
		super();
		this.id = id;
		this.content = content;
		this.responses = responses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<QuestionResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<QuestionResponse> responses) {
		this.responses = responses;
	}

	/*
	public Quiz getQuiz() {
		return quiz;
	}
	 */
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

}