package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.users.Student;
@Stateless
public class FYPSheetEJB implements FYPSheetEJBLocal{
	@PersistenceContext
	EntityManager service;

	@Override
	public FYPFile addFYPSheet(FYPFile file) {
		service.persist(file);
		return service.find(FYPFile.class, file.getId());
	}

	@Override
	public boolean removeFYPSheet(long fypsId) {
		FYPFile target = service.find(FYPFile.class, fypsId);
		if(target!=null) {
			service.remove(target);
			service.flush();
			return true;
		}
		return false;
	}

	@Override
	public FYPFile editFYPSheet(FYPFile toEdit) {
		return service.merge(toEdit);
	}

	@Override
	public FYPFile getFYFileById(long fypfileId) {
		return (FYPFile) service.createQuery("SELECT f from FYPFile f where f.id = :id")
				.setParameter("id", fypfileId).getSingleResult();
	}

	@Override
	public FYPFile getFypFileOfStudent(long studId) {
		Query q = service.createQuery("SELECT f from FYPFile fa, Student s where s.id = :id AND fa.id = s.fypFile.id")
				.setParameter("id", studId);
		List<Object> list = q.getResultList();
		if(!list.isEmpty()) {
			return  (FYPFile) list.get(0);
		}
		return null ;
	}

	@Override
	public List<FYPFile> getFYPfilesOfDepartment(long idDept) {
		Query q = service.createQuery("SELECT fa from FYPFile fa, Student f where "
				+ "fa.id = f.fypFile.id AND  "
				+ "f.studyClass.classOption.departement.id = :id")
				.setParameter("id", idDept);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null;
	}

	@Override
	public List<FYPFile> getAllAcceptedFYPSheets() {
		Query q =  service.createQuery("SELECT f from FYPFile f where f.fileStatus = :status").setParameter("status", FYPFileStatus.confirmed);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getAllSheetsWithNoMarks() {
		Query q =   service.createQuery("SELECT f from FYPFile f where (f.finalMark = NULL OR f.finalMark = 0 ) group by f.id"
				+ " HAVING (SELECT COUNT(i.id) FROM FYP_INTERVENTION i where i.internshipSheet.id = f.id AND (i.givenMark != NULL OR f.givenMark != 0) )=0");
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getFYPSheetsWithNoSupervisors() {
		Query q = service.createQuery("SELECT f from FYPFile f group by f.id "
				+ "HAVING (SELECT COUNT(i.id) from FYP_INTERVENTION i where i.internshipSheet.id = f.id) = 0");
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public FYPFile getFYPSById(long id) {
		Query q =  service.createQuery("SELECT f from FYPFile f where f.id = :id")
				.setParameter("id", id);
		List<Object> list = q.getResultList();
		if(!list.isEmpty()) {
			return  (FYPFile) list.get(0);
		}
		return null ;
	}

	@Override
	public List<FYPFile> getAllSheets() {
		Query q =  service.createQuery("SELECT f from FYPFile f ");
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getFYPSheetsOfTeacher(long idTeacher) {
		Query q =  service.createQuery("SELECT f from FYPFile f group by f.id"
				+ " HAVING (SELECT COUNT(i.id) from FYP_INTERVENTION i where i.teacher.id = :idt"
				+ " AND i.internshipSheet.id = f.id) > 0").setParameter("idt", idTeacher);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getAll() {
		return service.createQuery("SELECT f from FYPFile f ").getResultList();
	}

	@Override
	public FYPFile assignFYPFileToStudent(FYPFile file, long studentId) {
		Student s = service.find(Student.class, studentId);
		file.setStudent(s);
		s.setFypFile(file);
		service.persist(s);
		service.persist(file);
		service.flush();
		return service.find(FYPFile.class, file.getId());
	}

	@Override
	public List<FYPFile> getAllSheetsPending() {
		
		return service.createQuery("SELECT f from FYPFile f  where f.fileStatus =:status").setParameter("status", FYPFileStatus.pending).getResultList();
	}

}
