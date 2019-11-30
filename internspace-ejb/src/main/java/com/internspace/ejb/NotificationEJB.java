package com.internspace.ejb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.NotificationEJBLocal;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;
import com.internspace.entities.users.User;

@Stateless
public class NotificationEJB implements NotificationEJBLocal{
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Notification addNotification(long idFrom, long idTo, String content) {
		Notification notification = new Notification();
		notification.setContent(content);
		User reciever = em.find(User.class, idTo);
		User sender = em.find(User.class, idFrom);
		notification.setSender(sender);
		notification.setReciever(reciever);
		notification.setSeen(false);
		notification.setDateOfEmission(LocalDateTime.now());
		em.persist(notification);
		em.flush();
		return em.find(Notification.class, notification.getId());
	}

	@Override
	public boolean removeNotification(long notifId) {
		Notification n = em.find(Notification.class, notifId);
		if (n!=null) {
			em.remove(n);
			return true;
		}
		return false;
	}

	@Override
	public Notification editNotification(Notification notification) {
		em.merge(notification);
		em.flush();
		return em.find(Notification.class, notification.getId());
	}

	@Override
	public List<Notification> getNotificationHistoryOfUser(long studentId) {
		return em.createQuery("SELECT n from Notification n where n.reciever.id = :id order by n.dateOfEmission DESC")
				.setParameter("id", studentId).getResultList();
	}


	@Override
	public List<Notification> getAll() {
		return em.createQuery("SELECT n from Notification n ")
				.getResultList();
	}

	@Override
	public Notification getNotifById(long id) {
		return (Notification) em.createQuery("SELECT n from Notification n where n.id = "+id).getSingleResult();
	}

	@Override
	public List<Notification> getNotifsOfDepartment(long depId) {
		return em.createQuery("SELECT n from Notification n , User u, Student s where n.sender.department.id = :id AND n.id = :id "
				+ "AND n.reciever.department.id = :id")
				.setParameter("id", depId)
				.getResultList();
	}

}
