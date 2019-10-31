package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;

@Local
public interface StudentEJBLocal {
	
	
	public void addStudent(Student std);
	public  List<Student> getAll();
	public  List<Student> getAllStudentSaved();
	public  List<Student> getAllStudentLateYear();
	public Student enregistrerAuPlatforme(String cin);
	
	public void login(String cin);
	public Student authentification(String cin,String password);

	
	public  List<Student> getAllStudentAutorised();
	public  List<Student> getAllStudentNodisabled();
	public  List<FYPFile> getAllStudentFile();
	public FYPFile addFYPSheet(FYPFile file,long id);
	public  List<FYPFile> getAllStudentFileCin(String cin);
	public  List<FYPFeature> getAllStudentFileCinFeatures(String cin);
	public  List<Student> getAllStudentCin(String cin);


	void sendMail(String text,String cin);
	void mailEtat(String text,String cin);
	public List<Employee> getDirector(String cin);
	public List<FYPFile> getAllSheetsPendingStudent();
	public List<FYPFile> getAllSheetsPendingByStudent(String cin);
	
	
	
	

}
