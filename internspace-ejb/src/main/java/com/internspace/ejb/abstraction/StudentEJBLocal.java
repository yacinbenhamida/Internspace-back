package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.users.Student;

@Local
public interface StudentEJBLocal {
	
	
	public void addStudent(Student std);
	public  List<Student> getAll();
	public  List<Student> getAllStudentSaved();
	
	public void login(String cin);
	
	public  List<Student> getAllStudentdisabled();
	public  List<Student> getAllStudentNodisabled();
	public  List<FYPFile> getAllStudentCIN();

	void sendMail(String text,String cin);
	void mailEtat(String text,String cin);
	
	
	

}
