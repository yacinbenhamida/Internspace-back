package com.internspace.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.internspace.ejb.abstraction.QuizEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.quiz.QuestionResponse;
import com.internspace.entities.fyp.quiz.Quiz;
import com.internspace.entities.fyp.quiz.QuizQuestion;
import com.internspace.entities.fyp.quiz.StudentQuiz;
import com.internspace.entities.fyp.quiz.StudentQuizResponse;
import com.internspace.entities.users.Student;

@Stateless
public class QuizEJB implements QuizEJBLocal {

	@PersistenceContext
	EntityManager em;

	@Override
	public boolean createQuiz(Quiz quiz) {
		long categoryId = quiz.getCategory().getId();
		
		FYPCategory category = em.find(FYPCategory.class, categoryId);
		
		if(category == null)
			return false;
		
		quiz.setCategory(category);
		
		// Enforce quiz foreign key
		for(QuizQuestion question : quiz.getQuestions())
		{
			question.setQuiz(quiz);
			for(QuestionResponse response : question.getResponses())
			{
				response.setQuestion(question);
			}
		}
		
		em.merge(quiz.getCategory());
		em.persist(quiz);
		
		return true;
	}
	

	@Override
	public void addQuestion(Quiz quiz, QuizQuestion question) {
		quiz.getQuestions().add(question);
		em.persist(quiz);

	}

	@Override
	public List<QuizQuestion> getQuestionsByQuiz(long quizId) {

		TypedQuery<QuizQuestion> query = em.createQuery("SELECT Q FROM " + QuizQuestion.class.getName() + " Q WHERE Q.quiz.id = :quizId",
				QuizQuestion.class).setParameter("quiz", quizId);
		try {
			return query.getResultList();
		}

		catch (Exception e) {
			System.out.print("error");
		}
		return null;
	}

	@Override
	public StudentQuiz getOrCreateStudentQuiz(long studentId, long quizId) {
		List<StudentQuiz> studentQuizs = em
				.createQuery("SELECT UQ FROM " + StudentQuiz.class.getName() + " UQ"
						+ " JOIN FETCH UQ.quiz Q JOIN FETCH Q.questions QS"
						+ " WHERE UQ.student.id = :studentId AND UQ.quiz.id = :quizId", StudentQuiz.class)
				.setParameter("studentId", studentId).setParameter("quizId", quizId).getResultList();

		StudentQuiz studentQuiz = null;

		// Does it exist?
		if (studentQuizs == null || studentQuizs.size() == 0) {
			// Then create one

			Student student = em.find(Student.class, studentId);

			if (student == null) {
				System.out.println("Got a non-valid student id: " + studentId + ".");
				return null;
			}

			Quiz quiz = em.find(Quiz.class, quizId);

			if (quiz == null) {
				System.out.println("Got a non-valid quiz id: " + quizId + ".");
				return null;
			}

			studentQuiz = new StudentQuiz(student, quiz, 1);
			
			em.persist(studentQuiz);

		} else {
			studentQuiz = studentQuizs.get(0);
		}

		return studentQuiz;
	}

	@Override
	public Quiz getQuizOfCategoryWithLevel(long categoryId, int quizLevel)
	{
		Quiz quiz;
		
		// Check if requested quiz level is not above max quiz level
		boolean notAbove = quizLevel <= Quiz.getMaxquizlevel();
		
		if(!notAbove)
			return null;
		
		List<Quiz> quizs = em.createQuery("SELECT Q FROM " +  Quiz.class.getName() + " Q"
				+ " WHERE Q.category.id = :categoryId AND Q.requiredMinLevel = :quizLevel", Quiz.class)
				.setParameter("categoryId", categoryId)
				.setParameter("quizLevel", quizLevel)
				.getResultList();
		
		// Is there any???
		if(quizs == null || quizs.size() == 0)
			return null;
		
		quiz = quizs.get(0);
		
		return quiz;
	}
	
	@Override
	public List<Quiz> getAll()
	{
		List<Quiz> quizzes = em.createQuery("SELECT Q FROM " + Quiz.class.getName() + " Q"
				+ " JOIN FETCH Q.questions QQ",
				Quiz.class).getResultList();
		
		return quizzes;
	}

	@Override
	public List<Quiz> getAllByCategory(long categoryId)
	{
		List<Quiz> quizzes = em.createQuery("SELECT Q FROM " + Quiz.class.getName() + " Q"
				+ " JOIN FETCH Q.questions QQ"
				+ " WHERE Q.category.id = :categoryId",
				Quiz.class)
				.setParameter("categoryId", categoryId)
				.getResultList();
		
		return quizzes;
	}
	
	@Override
	public Quiz getQuizById(long quizId)
	{
		return em.find(Quiz.class, quizId);
	}
	
	@Override
	public boolean updateQuiz(Quiz updateQuiz)
	{
		if(updateQuiz == null)
			return false;
		
		Quiz quiz = getQuizById(updateQuiz.getId());
		
		if(quiz == null)
			return false;
		
		quiz.setTitle(updateQuiz.getTitle());
		quiz.setDescription(updateQuiz.getDescription());
		quiz.setRequiredMinLevel(updateQuiz.getRequiredMinLevel());
		quiz.setMinCorrectQuestionsPercentage(updateQuiz.getMinCorrectQuestionsPercentage());
		
		em.persist(em.contains(quiz) ? quiz : em.merge(quiz));
		
		return true;
	}
	
	@Override
	public void updateStudentQuiz(StudentQuiz studentQuiz)
	{
		em.persist(em.contains(studentQuiz) ? studentQuiz : em.merge(studentQuiz));
	}
	
	@Override
	public boolean deleteQuiz(long quizId)
	{
		Quiz quiz = getQuizById(quizId);
		
		if(quiz == null)
			return false;
		
		em.remove(quiz);
		
		return true;
	}
	
	@Override
	public Map<QuizQuestion, List<StudentQuizResponse>> getStudentQuizQuestionResponseMap(long userId, long quizId)
	{
		Map<QuizQuestion, List<StudentQuizResponse>> map = new HashMap<QuizQuestion, List<StudentQuizResponse>>();
		
		// Get all questions relevant to this quiz
		List<QuizQuestion> quizQuestions = em.createQuery("SELECT QQ FROM " + QuizQuestion.class.getName() + " QQ"
				+ " WHERE QQ.quiz.id = :quizId", QuizQuestion.class)
				.setParameter("quizId", quizId)
				.getResultList();

		for(QuizQuestion quizQuestion : quizQuestions)
		{
			System.out.println("Fetching Responses for quiz question with id: " + quizQuestion.getId());
			
			String queryStr = "SELECT UQR FROM " + StudentQuizResponse.class.getName() + " UQR"
					+ " WHERE UQR.response.question.id = :questionId";
			
			List<StudentQuizResponse> responses = em.createQuery(queryStr, StudentQuizResponse.class)
					.setParameter("questionId", quizQuestion.getId())
					.getResultList();
			
			map.put(quizQuestion, responses);
		}
		
	    return map;
	}

	@Override
	public List<Quiz> findQuizzesByTitle(String title, int n, boolean useLike)
	{
		String nameMatching;
		nameMatching = useLike ? "LIKE '%" + title.toLowerCase() + "%'" : "= '" + title.toLowerCase() + "'";
		
		String queryStr = "SELECT DISTINCT Q FROM " + Quiz.class.getName() + " Q"
				+ " WHERE lower(Q.title) " + nameMatching;

		List<Quiz> quizzes = em.createQuery(queryStr, Quiz.class).setMaxResults(n).getResultList();
		
		return quizzes;
		
	}
	

}
