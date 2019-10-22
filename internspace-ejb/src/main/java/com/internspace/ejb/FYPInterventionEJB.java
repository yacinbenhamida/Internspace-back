package com.internspace.ejb;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPInterventionEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;
@Stateless
public class FYPInterventionEJB implements FYPInterventionEJBLocal{
	@PersistenceContext
	EntityManager manager;
	@Override
	public FYPIntervention assignTeacherToFYPSheetWithRole(long idTeacher, long idFYPS, String role) {
		Employee teacher = manager.find(Employee.class, idTeacher);
		FYPFile sheet = manager.find(FYPFile.class, idFYPS);
		//check if the teacher isn't already affected
		Query q = manager.createQuery("SELECT COUNT(i.id) FROM "
				+ "FYP_INTERVENTION i where i.teacher.id = :id").setParameter("id", idTeacher);
		if(teacher != null && sheet != null && q.getResultList().isEmpty()) {
				FYPIntervention intervention = new FYPIntervention();
				intervention.setAssignmentDate(LocalDate.now());
				intervention.setInternshipSheet(sheet);
				intervention.setTeacher(teacher);
				intervention.setTeacherRole(this.convertRole(role));
				manager.persist(intervention);
				return manager.find(FYPIntervention.class, intervention.getId());
		}
		return null;
	}

	@Override
	public FYPIntervention editInterventionActorRole(long fypinterventionId, long idTeacher, String newRole) {
		Employee teacher = manager.find(Employee.class, idTeacher);
		FYPIntervention intervention = manager.find(FYPIntervention.class, fypinterventionId);
		//check if that a  teacher with that role isn't already affected
		Query q = manager.createQuery("SELECT COUNT(i.id) FROM "
				+ "FYP_INTERVENTION i where i.id = :id group by i.id"
				+ " HAVING (SELECT COUNT(interv.id) FROM FYP_INTERVENTION interv where interv.id = i.id AND"
				+ "  interv.teacherRole = :role ) = 0").setParameter("id", fypinterventionId)
				.setParameter("role", newRole);
		if(teacher != null && intervention != null && q.getResultList().isEmpty()) {
				intervention.setAssignmentDate(LocalDate.now());
				intervention.setTeacherRole(convertRole(newRole));
				manager.merge(intervention); 
				manager.flush();
				return manager.find(FYPIntervention.class, intervention.getId());
		}
		return null;
	}

	@Override
	public List<Employee> getAllTeachersRankedByNumberOfSupervisions() {
		// TODO Auto-generated method stub
		return null;
	}

	private FYPIntervention.TeacherRole convertRole(String role){
		switch (role) {
		case "juryPresident": return FYPIntervention.TeacherRole.juryPresident;
		case "preValidator " : 	return FYPIntervention.TeacherRole.preValidator;
		case "protractor" : return FYPIntervention.TeacherRole.protractor;
		case "supervisor" : return FYPIntervention.TeacherRole.supervisor;
		}
		return null;
	}
}
