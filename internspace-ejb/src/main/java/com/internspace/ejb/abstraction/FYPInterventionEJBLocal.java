package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;

@Local
public interface FYPInterventionEJBLocal {
	public FYPIntervention assignTeacherToFYPSheetWithRole(long idTeacher,long idFYPS,String role);
	public FYPIntervention editInterventionActorRole(long fypinterventionId,long idTeacher,String newRole);
	public FYPIntervention editInterventionActorRole(long fypinterventionId,String newRole,long idTeacher);
	public List<Employee> getAllTeachersRankedByNumberOfSupervisions();
	public List<FYPIntervention> getAll();
	public boolean deleteIntervention(long idInt);
	public List<Employee> getTeachersWhoPrefer(String subject);
	public FYPIntervention saveMark(int markValue, long idIntervention);
	public List<FYPIntervention> getInterventionsOfFYPSheet(long idFYPS);
	public List<FYPIntervention> getInterventionsOfTeacher(long idTeacher);
}
