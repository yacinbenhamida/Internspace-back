package com.internspace.ejb.abstraction;

import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;

@Local
public interface FYPInterventionEJBLocal {
	public FYPIntervention assignTeacherToFYPSheetWithRole(long idTeacher,long idFYPS,String role);
	public FYPIntervention editInterventionActorRole(long fypinterventionId,long idTeacher,String newRole);
	public Set<Employee> getAllTeachersRankedByNumberOfSupervisions();
}
