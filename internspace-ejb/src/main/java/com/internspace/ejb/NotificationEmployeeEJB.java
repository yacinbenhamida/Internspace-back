package com.internspace.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.NotificationEmployeeEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;

@Stateless
public class NotificationEmployeeEJB implements NotificationEmployeeEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Employee> getSuperviorsListWhoDidNotGiveGrades() {
		//Get FYPFiles of Students  Who has Submitted A Report
		List<FYPFile> FYPFileList = em.createQuery("SELECT s.fypFile FROM Student s WHERE s.hasSubmittedAReport = true")
				.getResultList();
		List<Employee> supervisorsList = new ArrayList<>();
		for(FYPFile fypf : FYPFileList) {
			List<Employee> e = em.createQuery("SELECT i.teacher from fyp_intervention i "
			+ "WHERE i.fypFile.id=" + fypf.getId() + " AND i.teacherRole='supervisor' AND i.givenMark=-1")
			.getResultList();
			if(!e.isEmpty()) {
				supervisorsList.add(e.get(0));
			}
		}
		return supervisorsList;
	}

	@Override
	public List<Employee> getReportersListWhoDidNotGiveGrades() {
		List<FYPFile> FYPFileList = em.createQuery("SELECT s.fypFile FROM Student s WHERE s.hasSubmittedAReport = true")
				.getResultList();
		List<Employee> reportersList = new ArrayList<>();
		for(FYPFile fypf : FYPFileList) {
			List<Employee> e = em.createQuery("SELECT i.teacher from fyp_intervention i "
			+ "WHERE i.fypFile.id=" + fypf.getId() + " AND i.teacherRole='reporter' AND i.givenMark=-1")
			.getResultList();
			if(!e.isEmpty()) {
				reportersList.add(e.get(0));
			}
		}
		return reportersList;
	}

	@Override
	public List<FYPFile> getFYPFileListeWaitingForPreValidation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FYPFile> getFYPFileListeMissingSupervisor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FYPFile> getFYPFileListeMissingReporter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendNotificationsToSuperviorsListWhoDidNotGiveGrades() {
		

	}

	@Override
	public void sendNotificationsToReportersListWhoDidNotGiveGrades() {
		// TODO Auto-generated method stub

	}

}
