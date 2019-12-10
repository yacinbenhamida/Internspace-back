package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.university.CompanyCoordinates;
import com.internspace.entities.university.Departement;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;

@Local
public interface InternshipDirectorEJBLocal {
	
	List<Student> getLateStudentsList(int dep, long id);
	void sendMail(int year, String text,long id);
	List<FYPFile> getAllFYPFileList();
	List<FYPFile> getFYPFileListByState(FYPFileStatus state);
	List<FYPFile> getFYPFileListByYear(int year);
	List<FYPFile> getFYPFileListByCountry(String location);
	List<FYPFile> getFYPFileListByCategory(String category);
	List<FYPFile> getFYPFileListSpecifique(int year , String location, FYPFileStatus state, String category);
	List<FYPFile> getFYPFileListCurrentYear(FYPFileStatus state);
	List<Object[]> getPendingFYPFile();
	void acceptFile(long id);
	void refuseFile(long id, String text);
	void acceptCancelingDemand(long id);
	void declineCancelingDemand(long id, String text);
	List<FYPFile> listCancelingDemand();
	public boolean disableAccount(long id);
	public List<Student> getAllStudentsList(long id);
	public Student FindStudent(String cin, long id);
	public Boolean ValidateSubmittedAReport(long id);
	public List<FYPFile> WaitingForDefensePlanningList();
	public FYPFile FilterWaitingForDefensePlanningList(String cin, String nom);
	public  void FixActionNumberAsSupervisor(int nb, long id);
	public  void FixActionNumberAsProtractor(int nb, long id);
	public  void FixActionNumberAsPreValidator(int nb, long id);
	public  void FixActionNumberAsJuryPresident(int nb, long id);
	public Boolean GetNameAndCountry (long id);
	public List<FYPSubject> FullStudentInfoWithVerifiedCompanys(long id);
	
	public List<Object[]> getPendingFYPFileWithLinks();
	public List<String> GetLinksOfCompany(long id);
	public List<Departement> getDepartements(long id);
	public CompanyCoordinates getCompanyCoordinates(long id);
	public Departement getDepartementInfo(long id);
	
	
	
	//partie teb3a rayén
	//Le directeur des stages pourra rendre l’étudiant autorisé à passer son PFE.
	public Student acceptPFE(long id);
	public Employee getInternshipDirectorById(long id);
	//acept modification majeure
	boolean acceptModification(long id);
	public FYPFile acceptPFEFyp(long id);
	public FYPFile cancelPFEFyp(long id);
	
	
	
	
	
	
}
