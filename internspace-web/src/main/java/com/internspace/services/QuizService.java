package com.internspace.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.internspace.ejb.abstraction.QuizEJBLocal;
import com.internspace.entities.fyp.quiz.Quiz;
import com.internspace.entities.fyp.quiz.QuizQuestion;
import com.internspace.entities.fyp.quiz.StudentQuiz;
import com.internspace.entities.fyp.quiz.StudentQuizResponse;

@Path("quiz")
public class QuizService {

	@Inject
	QuizEJBLocal service;

	// Quiz Section

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addQuiz(Quiz quiz) {
		String quizTitle = quiz.getTitle();
		if (quizTitle.isEmpty())
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid quiz title...").build();

		List<Quiz> quizzes = service.findQuizzesByTitle(quizTitle, 1, false);

		if (quizzes!= null && quizzes.size() > 0)
			return Response.status(Status.BAD_REQUEST).entity("Quiz with such name already exists: " + quizTitle)
					.build();

		boolean success = service.createQuiz(quiz);

		if(!success)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid category id, got: " + quiz.getCategory().getId())
					.build();
		
		return Response.status(Status.OK).entity(quiz).build();
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.status(Status.OK).entity(service.getAll()).build();
	}

	@GET
	@Path("/all/category")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllByCategory(@QueryParam(value = "category") long categoryId) {
		return Response.status(Status.OK).entity(service.getAllByCategory(categoryId)).build();
	}
	
	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateQuiz(Quiz updateQuiz) {
		if (updateQuiz.getId() == 0)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid quiz ID...").build();

		Quiz quiz = service.getQuizById(updateQuiz.getId());

		if (quiz == null)
			return Response.status(Status.BAD_REQUEST)
					.entity("Failed to find the quiz to update. Please provide a valid quiz ID...").build();

		service.updateQuiz(updateQuiz);

		return Response.status(Response.Status.OK)
				.entity("Successfully UPDATED quiz for ID: " + quiz.getId()).build();
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteQuiz(@QueryParam(value = "quiz") long quizId) {
		if (quizId == 0)
			return Response.status(Response.Status.BAD_REQUEST).build();

		Quiz quiz = service.getQuizById(quizId);

		if (quiz == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Can't find Quiz to delete with ID: " + quizId).build();

		service.deleteQuiz(quizId);
		return Response.status(Response.Status.OK).entity("Successfully DELETED Quiz for ID: " + quizId).build();
	}
	
	@POST
	@Path("/question/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addQuestionToQuiz(
			@QueryParam(value = "quiz") long quizId,
			QuizQuestion question
			) {

		Quiz quiz = service.getQuizById(quizId);
		
		if(quiz == null)
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid quiz id, got: " + quizId)
					.build();
		
		service.addQuestion(quiz, question);

		return Response.status(Status.OK).entity(question).build();
	}
	
	// Student Quiz Section

	@GET
	@Path("/student/quiz")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuizByCategoryAndLevel(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "category") long categoryId, @QueryParam(value = "level") int quizLevel)

	{
		StudentQuiz studentQuiz = service.getStudentQuizByCategoryAndLevel(studentId, categoryId, quizLevel);
		
		if (studentQuiz == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Can't find a matching StudentQuiz...").build();
		
		return Response.status(Response.Status.OK).entity(studentQuiz).build();
	}
	
	@GET
	@Path("/student/start-quiz")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrCreateStudentQuiz(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "quiz") long quizId) {
		
		StudentQuiz studentQuiz = service.getOrCreateStudentQuiz(studentId, quizId);
		
		if(studentQuiz == null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Failed to get or create a StudentQuiz for student id: " + studentId + " | quiz id: " + quizId).build();

		return Response.status(Response.Status.OK).entity(studentQuiz).build();
	}
	
	@GET
	//@POST
	@Path("/student/answer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserQuestionResponse(
			@QueryParam(value = "student") long studentId,
			@QueryParam(value = "response") long responseId,
			@QueryParam(value = "check") boolean toChecked)
	{
		StudentQuizResponse userQuizResponse = service.getOrCreateStudentQuestionResponse(studentId, responseId);

		System.out.println("updateUserQuestionResponse: studentId: " + studentId);
		System.out.println("updateUserQuestionResponse: responseId: " + responseId);
		System.out.println("updateUserQuestionResponse: toChecked: " + toChecked);
		
		if (userQuizResponse == null) {
			
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Got non-valid user quiz response with responseId: " + responseId).build();
		}

		System.out.println("INPUT: " + responseId + "|" + toChecked);

		userQuizResponse.setIsChecked(toChecked);
		service.updateStudentQuizResponse(userQuizResponse);

		return Response.status(Response.Status.OK).entity("userQuizResponse with id: " + userQuizResponse.getId() + " is now with checked=" + userQuizResponse.getIsChecked()).build();
	}
	
	@GET
	//@POST
	@Path("/student/finish-quiz")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response studentFinishQuiz(
			@QueryParam(value = "student") long studentId,
			@QueryParam(value = "quiz") long quizId)
	{
		float score = service.refreshStudentQuizScore(studentId, quizId);

		if(score < 0)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Make sure to finish your quiz before finishing: " + quizId).build();
		
		return Response.status(Response.Status.OK).build();
	}
	
	// User Quiz Related Category Preference Services
	
	@GET
	@Path("/student/categories/preferences/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrCreateStudentCategoryPreference(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "category") long categoryId) {
		return Response.status(Response.Status.OK).entity(this.service.getOrCreateStudentCategoryPreference(studentId, categoryId)).build();
	}
	
	@GET
	@Path("/student/quiz/responses-map/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentQuizQuestionResponseMap(@QueryParam(value = "student") long studentId,
			@QueryParam(value = "quiz") long quizId)
	{
		Map<QuizQuestion, List<StudentQuizResponse>> res = this.service.getStudentQuizQuestionResponseMap(studentId, quizId);
		
		// Index to out
		Map<Integer, List<StudentQuizResponse>> map = new HashMap<Integer, List<StudentQuizResponse>>();
		res.forEach((k,v)->map.put(k.getId(), v));
		
		return Response.status(Response.Status.OK).entity(map).build();
	}
	
}
