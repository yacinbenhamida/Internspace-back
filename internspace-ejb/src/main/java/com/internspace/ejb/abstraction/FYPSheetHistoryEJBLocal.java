package com.internspace.ejb.abstraction;

import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPSheetHistory;

@Local
public interface FYPSheetHistoryEJBLocal {
	public FYPSheetHistory recordOperation(FYPSheetHistory fypsheet);
	public FYPSheetHistory removeOperation(long idOP);
	public FYPSheetHistory editOperation(FYPSheetHistory fypsheeth);
	public Set<FYPSheetHistory> getAllRecordedOperations();
	public Set<FYPSheetHistory> getAllRecordedOperationsOfOneFYPSheet(long fypsheetId);
	}
