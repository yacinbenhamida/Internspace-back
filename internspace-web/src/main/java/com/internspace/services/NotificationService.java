package com.internspace.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.internspace.ejb.abstraction.NotificationEJBLocal;
import com.internspace.entities.exchanges.Notification;
import com.internspace.rest.utilities.filters.Secured;

@Path("notifications")
public class NotificationService {

	@Inject
	NotificationEJBLocal service;
	
	@GET
	@Path("add/{from}/{to}/{content}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveNotification(@PathParam("from") long idFrom,
			@PathParam("to") long idTo, @PathParam("content") String content) {
		Notification result = service.addNotification(idFrom,idTo,content);
		if(result != null) {
			return Response.status(Response.Status.OK).entity(result).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@PUT
	@Path("edit/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editNotification(@PathParam("id") long idN,String content) {
		Notification notification = service.getNotifById(idN);
		notification.setContent(content);
		Notification result = service.editNotification(notification);
		if(result != null) {
			return Response.status(Response.Status.OK).entity(result).build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	
	@DELETE
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNotification(
			@PathParam("id") long id) {
		boolean result = service.removeNotification(id);
		if(result) {
			return Response.status(Response.Status.OK).entity("removed").build();
		}
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}
	@GET
	@Path("history/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationHistoryOfUser(@PathParam("userId")long userId) {
		List<Notification> res = service.getNotificationHistoryOfUser(userId);
		System.out.println("result "+res.toString());
		if(res != null) {
			return Response.status(Response.Status.OK)
					.entity(res).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("no records").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		return Response.status(Response.Status.OK)
				.entity(service.getAll()).build();
		
	}

	
}
