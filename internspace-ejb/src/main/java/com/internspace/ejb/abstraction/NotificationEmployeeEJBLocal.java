package com.internspace.ejb.abstraction;

import java.util.List;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.users.Employee;

public interface NotificationEmployeeEJBLocal {
	List<Employee> getSuperviorsListWhoDidNotGiveGrades();
	List<Employee> getReportersListWhoDidNotGiveGrades();
	List<FYPFile> getFYPFileListeWaitingForPreValidation();
	List<FYPFile> getFYPFileListeMissingSupervisor();
	List<FYPFile> getFYPFileListeMissingReporter();
}
