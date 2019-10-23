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
import com.internspace.entities.fyp.FYPIntervention.TeacherRole;
import com.internspace.entities.users.Employee;
@Stateless
public class FYPInterventionEJB implements FYPInterventionEJBLocal{
	@PersistenceContext
	EntityManager manager;
	@Override
	public FYPIntervention assignTeacherToFYPSheetWithRole(long idTeacher, long idFYPS, String role) {
		Employee teacher = manager.find(Employee.class, idTeacher);
		FYPFile sheet = manager.find(FYPFile.class, idFYPS);
		//check if the teacher isn't already affected to that fypsheet
		Query q = manager.createQuery("SELECT i FROM "
				+ "FYP_INTERVENTION i where i.teacher.id = :id AND i.internshipSheet.id = :idints")
				.setParameter("id", idTeacher).setParameter("idints", idFYPS);
		if(teacher != null && sheet != null && q.getResultList().isEmpty() && teacher.getDepartment() !=null) {
			//.getInternship().getStudent to .getStudent
			if(sheet.getStudent().isHasSubmittedAReport()) {
				FYPIntervention intervention = new FYPIntervention();
				switch (role.toString()) {
					case "juryPresident": intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForPresidents());break;
					case "preValidator " : 	intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForPreValidators());break;
					case "protractor" : intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForProtractors());break;
					case "supervisor" : intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForSupervisors());break;
				}
					intervention.setAssignmentDate(LocalDate.now());
					//intervention.setInternshipSheet(sheet);
					intervention.setTeacher(teacher);
					intervention.setTeacherRole(this.convertRole(role));
					System.out.println(intervention);
					manager.persist(intervention);
				return manager.find(FYPIntervention.class, intervention.getId());
				}
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
		if(teacher != null && intervention != null && q.getResultList().isEmpty() && teacher.getDepartment() != null) {
				intervention.setAssignmentDate(LocalDate.now());
				intervention.setTeacherRole(convertRole(newRole));
				switch (newRole.toString()) {
					case "juryPresident": intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForPresidents());break;
					case "preValidator " : 	intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForPreValidators());break;
					case "protractor" : intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForProtractors());break;
					case "supervisor" : intervention.setActionsRemaining(teacher.getDepartment().getNumberOfActionsAllowedForSupervisors());break;
				}
				manager.merge(intervention); 
				manager.flush();
				return manager.find(FYPIntervention.class, intervention.getId());
		}
		return null;
	}

	@Override
	public List<Employee> getAllTeachersRankedByNumberOfSupervisions() {
		Query q = manager.createQuery("SELECT i.teacher,COUNT(i.id) as nb from FYP_INTERVENTION i where"
				+ " i.teacherRole = :role group by i.teacher.id order by nb DESC").setParameter("role", TeacherRole.supervisor);
		if(!q.getResultList().isEmpty()) {
			return q.getResultList();
		}
		return null;
	}

	private FYPIntervention.TeacherRole convertRole(String role){
		switch (role) {
		case "juryPresident": return FYPIntervention.TeacherRole.juryPresident;
		case "preValidator " : 	return FYPIntervention.TeacherRole.preValidator;
		case "protractor" : return FYPIntervention.TeacherRole.reporter;
		case "supervisor" : return FYPIntervention.TeacherRole.supervisor;
		}
		return null;
	}

	@Override
	public List<FYPIntervention> getAll() {
		Query q = manager.createQuery("SELECT i from FYP_INTERVENTION i ");
		if(!q.getResultList().isEmpty()) {
			return q.getResultList();
		}
		return null;
	}

	@Override
	public boolean deleteIntervention(long idInt) {
		if(manager.find(FYPIntervention.class, idInt)!=null) {
			manager.remove(manager.find(FYPIntervention.class, idInt));
			manager.flush();
			return true;
		}
		return false;
	}

	@Override
	public List<Employee> getTeachersWhoPrefer(String subject) {
		Query q = manager.createQuery("SELECT f from FYPCategory f ").setParameter("role", TeacherRole.supervisor);
		if(!q.getResultList().isEmpty()) {
			return q.getResultList();
		}
		return null;
	}

	@Override
	public FYPIntervention saveMark(int markValue, long idIntervention) {
		FYPIntervention intervention = manager.find(FYPIntervention.class, idIntervention);
			if(intervention!=null) {
				intervention.setActionsRemaining(intervention.getActionsRemaining()-1);
				intervention.setGivenMark(markValue);
				manager.merge(intervention);
				manager.flush();
				return intervention;
			}
		return null;
	}
}
