package com.internspace.ejb;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.entities.fyp.FYPFile;

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
		return (FYPFile) service.createQuery("SELECT f from fyp_file f where f.id = :id")
				.setParameter("id", fypfileId).getSingleResult();
	}

	@Override
	public FYPFile getFypFileOfStudent(long studId) {
		return (FYPFile) service.createQuery("SELECT f from fyp_file f where f.internship.student.id = :id")
				.setParameter("id", studId).getSingleResult();
	}

	@Override
	public List<FYPFile> getFYPfilesOfDepartment(long idDept) {
		return service.createQuery("SELECT f from fyp_file f where f.student.studyClass.departement.id = :id")
				.setParameter("id", idDept).getResultList();
	}

	@Override
	public List<FYPFile> getAllAcceptedFYPSheets() {
		return service.createQuery("SELECT f from fyp_file f where f.status = :status").setParameter("status", "confirmed")
				.getResultList();
	}

	@Override
	public List<FYPFile> getAllSheetsWithNoMarks() {
		return service.createQuery("SELECT f from fyp_file f where f.final_mark = NULL ")
				.getResultList();
	}

	@Override
	public List<FYPFile> getFYPSheetsWithNoSupervisors() {
		return service.createQuery("SELECT f from fyp_file f group by f.id "
				+ "HAVING (SELECT COUNT(i.id) from FYP_INTERVENTION i where i.internshipSheet.id = f.id) = 0")
				.getResultList();
	}

	@Override
	public FYPFile getFYPSById(long id) {
		return (FYPFile) service.createQuery("SELECT f from fyp_file f where f.id = :id")
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public List<FYPFile> getAllSheets() {
		return service.createQuery("SELECT f from fyp_file f ")
				.getResultList();
	}

}
