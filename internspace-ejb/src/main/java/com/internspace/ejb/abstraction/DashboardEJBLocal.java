package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.users.Student;

@Local
public interface DashboardEJBLocal {
	/*
	 * Dashboard STATS
	 */
	List<Student> getStudentsLocationDistribution(long uniId, boolean abroad);
	List<Student> getStudentsBySite(long siteId);
	
}
