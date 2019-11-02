package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
//rchm
@Local
public interface FYPSheetEJBLocal {
	public FYPFile addFYPSheet(FYPFile file);
	public boolean removeFYPSheet(long fypsId);
	public FYPFile editFYPSheet(FYPFile toEdit);
	public FYPFile assignFYPFileToStudent(FYPFile file,long studentId);
	
	public FYPFile getFYFileById(long fypfileId);
	public FYPFile getFypFileOfStudent(long studId);
	public FYPFile getFYPSById(long id);
	public List<FYPFile> getFYPfilesOfDepartment(long idDept);
	public List<FYPFile> getFYPSheetsOfTeacher(long idTeacher);
	// custom methods
	// returns the fyp sheets that got accepted by the internships director
	public List<FYPFile> getAllAcceptedFYPSheets();
	// no marks from reporter or supervisor
	public List<FYPFile> getAllSheetsWithNoMarks();
	public List<FYPFile> getFYPSheetsWithNoSupervisors();
	public List<FYPFile> getAllSheets();
	
	//my work
	public List<FYPFile> getAll();
	public List<FYPFile> getAllSheetsPending();
	public FYPFileStatus etatChanged(long id);
	public void modificationMajeur(FYPFile file);
	public FYPFile editFYPSheett(FYPFile file);


	


	
}
