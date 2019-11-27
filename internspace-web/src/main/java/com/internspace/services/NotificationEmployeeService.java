package com.internspace.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.NotificationEJB;
import com.internspace.ejb.abstraction.NotificationEmployeeEJBLocal;
import com.internspace.entities.users.Employee;

@Path("notification-employee")
@Stateless
public class NotificationEmployeeService {
	@Inject
	NotificationEmployeeEJBLocal notificationEmployeeService;
	NotificationEJB notificationService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSuperviorsListWhoDidNotGiveGrades() {
		List<Employee> superviorsListWhoDidNotGiveGrades = notificationEmployeeService.getSuperviorsListWhoDidNotGiveGrades();
		/*for(Employee e: superviorsListWhoDidNotGiveGrades) {
			notificationService.addNotification(1, 2, "You forgot you give marks");
		}*/
		
		return Response.status(Response.Status.OK)
				.entity(notificationEmployeeService.getSuperviorsListWhoDidNotGiveGrades()).build();
		
	}
}
