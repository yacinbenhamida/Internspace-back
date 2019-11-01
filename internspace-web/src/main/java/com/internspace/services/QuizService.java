package com.internspace.services;

import java.util.List;

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
	
	
	// Student Quiz Section
	
	public Response getQuizzesByCategory()
	{
		
	}
}
