package com.internspace.entities.fyp.quiz;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_response")
public class QuestionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_id")
	private long id;

	private boolean isCorrect;
	private String content;

	@ManyToOne(optional = false, cascade=CascadeType.ALL)
	@JoinColumn(name = "quesiton", insertable = true, updatable = true)
	private QuizQuestion question;

	public QuestionResponse() {
	}

	public QuestionResponse(long id, boolean isCorrect, String content, QuizQuestion question) {
		super();
		this.id = id;
		this.isCorrect = isCorrect;
		this.content = content;
		this.question = question;
	}

	// @OneToMany(mappedBy="response")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/*
	public QuizQuestion getQuestion() {
		return question;
	}
	 */
	
	public void setQuestion(QuizQuestion question) {
		this.question = question;
	}

}
