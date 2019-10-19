package com.internspace.ejb.abstraction;

import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;

@Local
public interface FYPSheetEJBLocal {
	public FYPFile addFYPSheet(FYPFile file);
	public FYPFile removeFYPSheet(long fypsId);
	public FYPFile editFYPSheet(FYPFile toEdit);
	
	public FYPFile getFYFileById(long fypfileId);
	public FYPFile getFypFileOfStudent(long studId);
	public FYPFile getFYPfilesOfDepartment(long idDept);
	
	// custom methods
	// returns the fyp sheets that got accepted by the internships director
	public Set<FYPFile> getAllAcceptedFYPSheets();
	// no marks from reporter or supervisor
	public Set<FYPFile> getAllSheetsWithNoMarks();
	public Set<FYPFile> getFYPSheetsWithNoSupervisors();
	
}
