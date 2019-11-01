package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.internspace.entities.fyp.quiz.QuestionResponse;
import com.internspace.entities.fyp.quiz.Quiz;
import com.internspace.entities.fyp.quiz.QuizQuestion;
import com.internspace.entities.fyp.quiz.StudentQuiz;
import com.internspace.entities.fyp.quiz.StudentQuizResponse;

@Local
public interface QuizEJBLocal {

	public boolean createQuiz(Quiz quiz);
	public void addQuestion(Quiz quiz, QuizQuestion question);
	public List<Quiz> getAll();
	public List<Quiz> getAllByCategory(long categoryId);
	public List<QuizQuestion> getQuestionsByQuiz(long quizId);
	public Quiz getQuizById(long quizId);
	public boolean updateQuiz(Quiz updateQuiz);
	public boolean deleteQuiz(long quizId);
	public List<QuestionResponse> getQuestionResponsesOfQuiz(long quizId);
	
	public StudentQuiz getOrCreateStudentQuiz(long studentId, long quizId);
	public StudentQuizResponse getOrCreateUserQuestionResponse(long studentId, long responseId);
	public void updateUserQuizResponse(StudentQuizResponse userQuizResponse);
	public StudentQuiz getStudentQuizByCategoryAndLevel(long studentId, long categoryId, int quizLevel);
	public Quiz getQuizOfCategoryWithLevel(long categoryId, int quizLevel);
	void updateStudentQuiz(StudentQuiz studentQuiz);
	
	
	public Map<QuizQuestion, List<StudentQuizResponse>> getStudentQuizQuestionResponseMap(long studentId, long quizId);
	public List<Quiz> findQuizzesByTitle(String title, int n, boolean useLike);
	
}
