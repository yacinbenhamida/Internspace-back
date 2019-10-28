package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.NotificationEJBLocal;
import com.internspace.entities.exchanges.Notification;

@Path("notifications")
public class NotificationService {

	@Inject
	NotificationEJBLocal service;
	
	@GET
	@Path("add/{from}/{to}/{content}/{direction}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveNotification(@PathParam("from") long idFrom,
			@PathParam("to") long idTo, @PathParam("content") String content
			,@PathParam("direction") String direction) {
		Notification result = service.addNotification(idFrom,idTo,content,direction);
		if(result != null) {
			return Response.status(Response.Status.OK).entity(result).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@POST
	@Path("edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editNotification(Notification n) {
		Notification result = service.editNotification(n);
		if(result != null) {
			return Response.status(Response.Status.OK).entity(n).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@DELETE
	@Path("delete/{id}")
	public Response deleteNotification(long id) {
		boolean result = service.removeNotification(id);
		if(result) {
			return Response.status(Response.Status.OK).entity("removed").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@GET
	@Path("history/student/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationHistoryOfStudent(@PathParam("studentId")long studentId) {
		List<Notification> res = service.getNotificationHistoryOfStudent(studentId);
		System.out.println("result "+res.toString());
		if(res != null) {
			return Response.status(Response.Status.OK)
					.entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND)
				.entity("no records").build();
		
	}
	@GET
	@Path("history/employee/{empId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationHistoryOfEmployee(@PathParam("empId")int empId) {
		return Response.status(Response.Status.OK)
				.entity(service.getNotificationHistoryOfEmployee(empId)).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		return Response.status(Response.Status.OK)
				.entity(service.getAll()).build();
		
	}
}
