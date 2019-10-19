package com.internspace.ejb.abstraction;

import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.exchanges.Notification;

@Local
public interface NotificationEJBLocal {
	public Notification addNotification(Notification notification);
	public boolean removeNotification(long notifId);
	public Notification editNotification(Notification notification);
	public Set<Notification> getNotificationHistoryOfStudent(long studentId);
	public Set<Notification> getNotificationHistoryOfEmployee(long employeeId);
	public Set<Notification> getAll();
}
