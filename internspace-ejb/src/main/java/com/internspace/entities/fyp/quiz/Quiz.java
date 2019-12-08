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

import com.internspace.entities.fyp.FYPCategory;

@Entity
@Table(name = "quiz")
public class Quiz implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int maxQuizLevel = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_id")
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "icon_url")
	private String iconUrl;
	
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "category", insertable = true, updatable = true)
	private FYPCategory category;

	@OneToMany(mappedBy = "quiz", orphanRemoval = true, cascade = CascadeType.ALL, fetch =FetchType.EAGER)
	private List<QuizQuestion> questions;

	@Column(name = "required_min_level", columnDefinition = "int default 1")
	private int requiredMinLevel;

	@Column(name = "min_correct_questions_percentage", columnDefinition = "int default 60")
	private int minCorrectQuestionsPercentage; // 0 -> 100

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconUrl()
	{
		return this.iconUrl;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FYPCategory getCategory() {
		return category;
	}

	public void setCategory(FYPCategory category) {
		this.category = category;
	}

	public List<QuizQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuizQuestion> questions) {
		this.questions = questions;
	}

	public int getRequiredMinLevel() {
		return requiredMinLevel;
	}

	public void setRequiredMinLevel(int requiredMinLevel) {
		this.requiredMinLevel = requiredMinLevel;
	}

	public int getMinCorrectQuestionsPercentage() {
		return minCorrectQuestionsPercentage;
	}

	public void setMinCorrectQuestionsPercentage(int minCorrectQuestionsPercentage) {
		this.minCorrectQuestionsPercentage = minCorrectQuestionsPercentage;
	}

	public static int getMaxquizlevel() {
		return maxQuizLevel;
	}

}
