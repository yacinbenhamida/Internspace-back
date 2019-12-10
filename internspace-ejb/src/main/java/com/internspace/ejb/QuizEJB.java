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
import com.internspace.entities.fyp.StudentCategoryPreference;
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
		
		question.setQuiz(quiz);
		for(QuestionResponse response : question.getResponses())
		{
			response.setQuestion(question);
		}
		
		// em.merge(question);
		em.persist(em.contains(quiz) ? quiz : em.merge(quiz));
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

		// Does it exist?
		if (studentQuizs == null || studentQuizs.size() == 0) {
			// Then create one

			studentQuiz = new StudentQuiz(student, quiz, 0);

			em.persist(studentQuiz);

		} else {
			studentQuiz = studentQuizs.get(0);
		}

		// Always do this
		for (QuestionResponse questionResponse : getQuestionResponsesOfQuiz(quiz.getId())) {
			getOrCreateStudentQuestionResponse(studentId, questionResponse.getId());
		}
		
		return studentQuiz;
	}

	@Override
	public List<QuestionResponse> getQuestionResponsesOfQuiz(long quizId)
	{
		List<QuestionResponse> questionResponses = em.createQuery("SELECT QR FROM " + QuestionResponse.class.getName() + " QR"
				+ " WHERE QR.question.quiz.id = :quizId", QuestionResponse.class)
				.setParameter("quizId", quizId)
				.getResultList();
		
		return questionResponses;
	}
	
	@Override
	public StudentQuizResponse getOrCreateStudentQuestionResponse(long studentId, long responseId)
	{
		List<StudentQuizResponse> studentQuizResponses = em
				.createQuery("SELECT UQR FROM " + StudentQuizResponse.class.getName() + " UQR"
						+ " WHERE UQR.student.id = :studentId AND UQR.response.id = :responseId", StudentQuizResponse.class)
				.setParameter("studentId", studentId)
				.setParameter("responseId", responseId)
				.getResultList();

		StudentQuizResponse studentQuizResponse = null;

		// Does it exist?
		if (studentQuizResponses == null || studentQuizResponses.size() == 0) {
			// Then create one

			Student student = em.find(Student.class, studentId);

			if (student == null) {
				System.out.println("Got a non-valid Student id: " + studentId + ".");
				return null;
			}

			QuestionResponse response = em.find(QuestionResponse.class, responseId);

			if (response == null) {
				System.out.println("Got a non-valid response id: " + responseId + ".");
				return null;
			}

			studentQuizResponse = new StudentQuizResponse(student, response, false);
			em.persist(studentQuizResponse);

		} else {
			studentQuizResponse = studentQuizResponses.get(0);
		}

		return studentQuizResponse;
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
		List<Quiz> quizzes = em.createQuery("SELECT DISTINCT Q FROM " + Quiz.class.getName() + " Q"
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

	
	@Override
	public StudentQuiz getStudentQuizByCategoryAndLevel(long studentId, long categoryId, int quizLevel) {
		
		Quiz quiz = getQuizOfCategoryWithLevel(categoryId, quizLevel);
		
		if(quiz == null)
			return null;
		
		// StudentQuiz
		return getOrCreateStudentQuiz(studentId, quiz.getId());
	}
	
	@Override
	public void updateStudentQuizResponse(StudentQuizResponse userQuizResponse)
	{
		em.persist(em.contains(userQuizResponse) ? userQuizResponse : em.merge(userQuizResponse));
	}

	@Override
	public StudentCategoryPreference getOrCreateStudentCategoryPreference(long studentId, long categoryId)
	{
		List<StudentCategoryPreference> scps = em
				.createQuery("SELECT SCP FROM " + StudentCategoryPreference.class.getName() + " SCP"
						+ " WHERE SCP.student.id = :studentId AND SCP.category.id = :categoryId", StudentCategoryPreference.class)
				.setParameter("studentId", studentId)
				.setParameter("categoryId", categoryId)
				.getResultList();

		StudentCategoryPreference scp = null;

		// Does it exist?
		if (scps == null || scps.size() == 0) {
			// Then create one

			Student student = em.find(Student.class, studentId);

			if (student == null) {
				System.out.println("Got a non-valid student id: " + studentId + ".");
				return null;
			}

			FYPCategory category = em.find(FYPCategory.class, categoryId);

			if (category == null) {
				System.out.println("Got a non-valid category id: " + categoryId + ".");
				return null;
			}

			// 0 for actual relation level, to start with quiz 1, and so...
			scp = new StudentCategoryPreference(student, category, 0);
			em.persist(scp);

		} else {
			scp = scps.get(0);
		}

		return scp;
	}
	
	@Override
	public float refreshStudentQuizScore(long studentId, long quizId) {
		
		System.out.println("SHOWING QUIZ RESULT!");
		Map<QuizQuestion, List<StudentQuizResponse>> quizQToUserResponseMap;
		
		StudentQuiz studentQuiz = getOrCreateStudentQuiz(studentId, quizId);
		
		if(studentQuiz == null)
			return -1f;

		quizQToUserResponseMap = getStudentQuizQuestionResponseMap(studentId, quizId);
		
		// Invalid
		if (quizQToUserResponseMap == null)
			return -1f;

		float correctQuestionsCount = 0;
		float questionsCount = quizQToUserResponseMap.keySet().size();

		System.out.println("questionsCount: " + questionsCount);
		
		for (QuizQuestion key : quizQToUserResponseMap.keySet()) {

			boolean questionAnsweredCorrectly = true;

			for (StudentQuizResponse uqr : quizQToUserResponseMap.get(key)) {
				// We at least found a wrong answer
				System.out.println("Checking uqr.isChecked: " + uqr.getIsChecked() + " WITH isCorrect: " + uqr.getResponse().getIsCorrect());
				
				if (uqr.getIsChecked() != uqr.getResponse().getIsCorrect()) {
					questionAnsweredCorrectly = false;
					break;
				}
			}

			if (questionAnsweredCorrectly)
				++correctQuestionsCount;
		}
		
		float correctAnswersPercentage = 0f;
		correctAnswersPercentage = (int) (correctQuestionsCount * 100 / questionsCount);

		studentQuiz.setScore((int) correctAnswersPercentage);

		StudentCategoryPreference scp = getOrCreateStudentCategoryPreference(studentId, studentQuiz.getQuiz().getCategory().getId());
		
		// Check if this percentage is enough to pass the quiz.
		if (correctAnswersPercentage >= studentQuiz.getQuiz().getMinCorrectQuestionsPercentage()) {
			int res = Math.max(scp.getSkillScore(), studentQuiz.getQuiz().getRequiredMinLevel() + 1);
			res = Math.min(res, 10);
			
			scp.setSkillScore(res);
			updateStudentCategoryPreference(scp);
		}

		return correctAnswersPercentage;
	}

	@Override
	public void updateStudentCategoryPreference(StudentCategoryPreference scp)
	{
		em.persist(em.contains(scp) ? scp : em.merge(scp));
	}
	
	
	@Override
	public QuizQuestion getNextQuestionOrFinishStudentQuiz(long studentId, long quizId, int currQuestuionIdx) {

		System.out.println("nextQuestion called!");

		StudentQuiz studentQuiz = getOrCreateStudentQuiz(studentId, quizId);
		
		List<QuizQuestion> quizQuestions = getQuestionsByQuiz(quizId);

		if (quizQuestions == null || quizQuestions.size() == 0)
			return null;

		int targetQuestionIndex = Math.max(0,
				Math.min(quizQuestions.size() - 1, studentQuiz.getCurrentQuestionIndex() + 1));
		
		System.out.println(targetQuestionIndex);

		boolean finished = targetQuestionIndex == studentQuiz.getCurrentQuestionIndex();

		// Refresh student quiz score and return same object to inform that there weren't any new
		if (finished) {
			refreshStudentQuizScore(studentId, quizId);
			return quizQuestions.get(targetQuestionIndex);
		}

		// Update the index
		studentQuiz.setCurrentQuestionIndex(targetQuestionIndex);
		updateStudentQuiz(studentQuiz);

		// QuizQuestion quizQuestion =
		// quizQuestions.get(userQuiz.getCurrentQuestionIndex());

		return quizQuestions.get(targetQuestionIndex);
	}

	@Override
	public QuizQuestion tryGetPrevQuestion(long studentId, long quizId, int curQuestionIdx) {
		System.out.println("previousQuestion called!");

		StudentQuiz studentQuiz = getOrCreateStudentQuiz(studentId, quizId);
		List<QuizQuestion> quizQuestions = getQuestionsByQuiz(quizId);
		
		if (quizQuestions == null || quizQuestions.size() == 0)
			return null;

		// Refresh student quiz score and return same object to inform that there weren't any new
		if (studentQuiz.getCurrentQuestionIndex() <= 0) {
			studentQuiz.setCurrentQuestionIndex(0);
			updateStudentQuiz(studentQuiz);
			return quizQuestions.get(0);
		}

		int targetQuestionIndex = studentQuiz.getCurrentQuestionIndex() - 1;
		studentQuiz.setCurrentQuestionIndex(targetQuestionIndex);
		updateStudentQuiz(studentQuiz);

		return quizQuestions.get(targetQuestionIndex);
	}

}
