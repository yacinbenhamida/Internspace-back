package com.internspace.ejb;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.NotificationEJBLocal;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.exchanges.Notification.Direction;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;

@Stateless
public class NotificationEJB implements NotificationEJBLocal{
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Notification addNotification(long idFrom, long idTo, String content,String direction) {
		Notification notification = new Notification();
		notification.setContent(content);
		if(direction.equals("employeeToStudent")) {
			Student s = em.find(Student.class, idTo);
			Employee e = em.find(Employee.class, idFrom);
			notification.setEmployee(e);
			notification.setStudent(s);
			notification.setSeen(false);
			notification.setDirection(Direction.fromEmployeeToStudent);
		}
		else {
			Student s = em.find(Student.class, idFrom);
			Employee e = em.find(Employee.class, idTo);
			notification.setEmployee(e);
			notification.setStudent(s);
			notification.setSeen(false);
			notification.setDirection(Direction.fromStudentToEmployee);
		}
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
