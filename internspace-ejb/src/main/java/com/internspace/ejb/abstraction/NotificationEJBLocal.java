package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.exchanges.Notification;

@Local
public interface NotificationEJBLocal {
	public Notification addNotification(long idFrom, long idTo, String content,String direction);
	public boolean removeNotification(long notifId);
	public Notification editNotification(Notification notification);
	public List<Notification> getNotificationHistoryOfStudent(long studentId);
	public List<Notification> getNotificationHistoryOfEmployee(long employeeId);
	public List<Notification> getAll();
}
