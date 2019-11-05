package com.internspace.ejb;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPSheetHistoryEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPSheetHistory;

import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
@Stateless
public class FYPSheetHistoryEJB implements FYPSheetHistoryEJBLocal{
	@PersistenceContext
	EntityManager entityManager;
	@Override
	public long recordOperation(FYPSheetHistory fypsheet) {
		entityManager.persist(fypsheet);
		entityManager.flush();
		return fypsheet.getId();
	}

	@Override
	public void removeOperation(long idOP) {
		entityManager.remove(entityManager.find(FYPSheetHistory.class, idOP));
		entityManager.flush();
	}

	@Override
	public FYPSheetHistory editOperation(FYPSheetHistory fypsheeth) {
		entityManager.merge(fypsheeth);
		entityManager.flush();
		return entityManager.find(FYPSheetHistory.class, fypsheeth.getId());
	}

	@Override
	public List<FYPSheetHistory> getAllRecordedOperations() {
		return entityManager.createQuery("SELECT f FROM fyp_sheet_history f").getResultList();
	}

	@Override
	public List<FYPSheetHistory> getAllRecordedOperationsOfOneFYPSheet(long fypsheetId) {
		
		return entityManager.createQuery("SELECT f FROM fyp_sheet_history f where f.changedFile.id=:id")
				.setParameter("id", fypsheetId).getResultList();

	}

	@Override
	public FYPSheetHistory getById(long id) {
		return entityManager.find(FYPSheetHistory.class, id);
	}

	@Override
	public void addFYPSheet(FYPFile file) {
		FYPSheetHistory f = new FYPSheetHistory();
		
		f.setChangedFile(file);
		f.setOldState(file.getFileStatus());
		
		entityManager.persist(f);
		entityManager.flush();
	}
	
	// list all fypfile in  FYPSheetHistory

	@Override
	public List<FYPFile> getAllFiles() {
		return entityManager.createQuery("SELECT f.changedFile FROM "+ FYPSheetHistory.class.getName()+" f").getResultList();
	}

}
