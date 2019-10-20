package com.internspace.ejb;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.NotificationEJBLocal;
import com.internspace.entities.exchanges.Notification;

@Stateless
public class NotificationEJB implements NotificationEJBLocal{
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Notification addNotification(Notification notification) {
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
	public List<Notification> getNotificationHistoryOfStudent(long studentId) {
		return em.createQuery("SELECT n from Notification n where n.student.id = :id")
				.setParameter("id", studentId).getResultList();
	}

	@Override
	public List<Notification> getNotificationHistoryOfEmployee(long employeeId) {
		return em.createQuery("SELECT n from Notification n where n.employee.id = :id")
				.setParameter("id", employeeId).getResultList();
	}

	@Override
	public List<Notification> getAll() {
		return em.createQuery("SELECT n from Notification n")
				.getResultList();
	}

}
