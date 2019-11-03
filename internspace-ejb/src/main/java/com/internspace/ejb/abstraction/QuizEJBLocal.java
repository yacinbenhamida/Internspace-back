package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.internspace.entities.fyp.StudentCategoryPreference;
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
	public List<QuestionResponse> getQuestionResponsesOfQuiz(long quizId);
	public Quiz getQuizById(long quizId);
	public Quiz getQuizOfCategoryWithLevel(long categoryId, int quizLevel);
	public boolean updateQuiz(Quiz updateQuiz);
	public boolean deleteQuiz(long quizId);
	
	public StudentQuiz getStudentQuizByCategoryAndLevel(long studentId, long categoryId, int quizLevel);
	public StudentQuiz getOrCreateStudentQuiz(long studentId, long quizId);
	public StudentQuizResponse getOrCreateStudentQuestionResponse(long studentId, long responseId);
	public float refreshStudentQuizScore(long studentId, long quizId);
	public QuizQuestion getNextQuestionOrFinishStudentQuiz(long studentId, long quizId, int currQuestuionIdx);
	public QuizQuestion tryGetPrevQuestion(long studentId, long quizId, int curQuestionIdx);
	public void updateStudentQuizResponse(StudentQuizResponse userQuizResponse);
	void updateStudentQuiz(StudentQuiz studentQuiz);
	
	public StudentCategoryPreference getOrCreateStudentCategoryPreference(long studentId, long categoryId);
	public void updateStudentCategoryPreference(StudentCategoryPreference scp);
	public Map<QuizQuestion, List<StudentQuizResponse>> getStudentQuizQuestionResponseMap(long studentId, long quizId);
	public List<Quiz> findQuizzesByTitle(String title, int n, boolean useLike);
	
}
