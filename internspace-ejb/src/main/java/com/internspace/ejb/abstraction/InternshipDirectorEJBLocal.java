package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.users.Student;

@Local
public interface InternshipDirectorEJBLocal {
	
	List<Student> getLateStudentsList(int dep);
	void sendMail(int year);
	List<FYPFile> getAllFYPFileList();
	List<FYPFile> getFYPFileListByState(String state);
	List<FYPFile> getFYPFileListByYear(int year);
	List<FYPFile> getFYPFileListByCountry(String location);
	List<FYPFile> getFYPFileListSpecifique();
	
	
	
	
}
