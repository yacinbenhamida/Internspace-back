package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;

import com.internspace.entities.university.StudyClass;
import com.internspace.entities.users.Employee;
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
	void acceptFile(long id);
	void refuseFile(long id, String text);
	void acceptCancelingDemand(long id);
	void declineCancelingDemand(long id, String text);
	List<FYPFile> listCancelingDemand();
	public boolean disableAccount(long id);
	public List<Student> getAllStudentsList();
	public Student FindStudent(long id);
	public Boolean ValidateSubmittedAReport(long id);
	public List<FYPFile> WaitingForDefensePlanningList();
	public FYPFile FilterWaitingForDefensePlanningList(String cin, String nom);
	public  void FixActionNumberAsSupervisor(int nb, int id);
	public  void FixActionNumberAsProtractor(int nb, int id);
	public  void FixActionNumberAsPreValidator(int nb, int id);
	public  void FixActionNumberAsJuryPresident(int nb, int id);
	
	//Le directeur des stages pourra rendre l’étudiant autorisé à passer son PFE.
	void acceptPFE(long id);
	public Employee getInternshipDirectorById(long id);
	
	
	
}
