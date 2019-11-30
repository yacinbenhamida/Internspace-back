package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.exchanges.Notification;

@Local
public interface NotificationEJBLocal {
	public Notification addNotification(long idFrom, long idTo, String content);
	public boolean removeNotification(long notifId);
	public Notification editNotification(Notification notification);
	public List<Notification> getNotificationHistoryOfUser(long studentId);
	public Notification getNotifById(long id);
	public List<Notification> getAll();
	public List<Notification> getNotifsOfDepartment(long depId);
	
}
