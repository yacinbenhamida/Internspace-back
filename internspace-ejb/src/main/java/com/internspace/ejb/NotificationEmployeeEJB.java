package com.internspace.ejb;

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
		//Get Students List Who has Submitted A Report
		List<FYPFile> FYPFileList = em.createQuery("SELECT s.fypFile FROM STUDENT s WHERE s.hasSubmittedAReport = true")
				.getResultList();
		return null;
	}

	@Override
	public List<Employee> getReportersListWhoDidNotGiveGrades() {
		// TODO Auto-generated method stub
		return null;
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

}
