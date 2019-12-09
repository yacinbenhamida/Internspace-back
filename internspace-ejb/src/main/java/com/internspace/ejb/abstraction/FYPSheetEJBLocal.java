package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.users.Student;
//rchm
@Local
public interface FYPSheetEJBLocal {
	public FYPFile addFYPSheet(FYPFile file);
	public boolean removeFYPSheet(long fypsId);
	public FYPFile editFYPSheet(FYPFile toEdit);
	public FYPFile assignFYPFileToStudent(FYPFile file,long studentId);
	public List<FYPFile> allFYPfilesWatingForMarkFrom();
	
	public FYPFile getFYFileById(long fypfileId);
	public FYPFile getFypFileOfStudent(long studId);
	public FYPFile getFYPSById(long id);
	public List<FYPFile> getFYPfilesOfDepartment(long idDept);
	public List<FYPFile> getFYPSheetsOfTeacher(long idTeacher);
	// custom methods
	// returns the fyp sheets that got accepted by the internships director
	public List<FYPFile> getAllAcceptedFYPSheets(long idDep);
	// no marks from reporter or supervisor
	public List<FYPFile> getAllSheetsWithNoMarks();
	public List<FYPFile> getFYPSheetsWithNoSupervisors(long idDep);
	public List<FYPFile> getAllSheets();
	
	//my work
	public List<FYPFile> getAll();
	public List<FYPFile> getAllSheetsPending();
	public FYPFileStatus etatChanged(long id);
	public boolean modificationMajeur(long id);
	public FYPFile editFYPSheett(FYPFile file);
	public FYPFile editFYPSheetStudent(FYPFile file,long id );
	public FYPFile editFYPSheetStudentMaj(FYPFile file,long id );
	public FYPFile acceptPFE(long id);
	public FYPFile accept(long id);
	
	public FYPFile cancel(long id);

	


	
}
