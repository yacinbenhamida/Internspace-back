package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPSheetHistory;

@Local
public interface FYPSheetHistoryEJBLocal {
	public long recordOperation(FYPSheetHistory fypsheet);
	public void addFYPSheet(FYPFile file);
	public void removeOperation(long idOP);
	
	public FYPSheetHistory getById(long id);
	public FYPSheetHistory editOperation(FYPSheetHistory fypsheeth);
	public List<FYPSheetHistory> getAllRecordedOperations();
	public List<FYPSheetHistory> getAllRecordedOperationsOfOneFYPSheet(long fypsheetId);
	
	public List<FYPFile> getAllFiles();
	}
