package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.users.Student;

@Local
public interface InternshipDirectorEJBLocal {
	
	List<Student> getLateStudentsList(int dep);
	void sendMail(int year, String text);
	List<FYPFile> getAllFYPFileList();
	List<FYPFile> getFYPFileListByState(FYPFileStatus state);
	List<FYPFile> getFYPFileListByYear(int year);
	List<FYPFile> getFYPFileListByCountry(String location);
	List<FYPFile> getFYPFileListSpecifique(int year , String location, FYPFileStatus state);
	List<FYPFile> getFYPFileListCurrentYear(FYPFileStatus state);
	void acceptFile(int id);
	void refuseFile(int id, String text);
	void acceptCancelingDemand(int id);
	void declineCancelingDemand(int id, String text);
	List<FYPFile> listCancelingDemand();
	
	
	
	
}
