package com.internspace.ejb;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPSheetHistoryEJBLocal;
import com.internspace.entities.fyp.FYPSheetHistory;
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
		entityManager.persist(fypsheeth);
		entityManager.flush();
		return entityManager.find(FYPSheetHistory.class, fypsheeth.getId());
	}

	@Override
	public List<FYPSheetHistory> getAllRecordedOperations() {
		return entityManager.createQuery("SELECT f FROM fyp_sheet_history f").getResultList();
	}

	@Override
	public List<FYPSheetHistory> getAllRecordedOperationsOfOneFYPSheet(long fypsheetId) {
		
		return entityManager.createQuery("SELECT f FROM FYPSheetHistory f where f.changedFile.id=:id")
				.setParameter(0, fypsheetId).getResultList();

	}

	@Override
	public FYPSheetHistory getById(long id) {
		return entityManager.find(FYPSheetHistory.class, id);
	}

}