package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;


import com.internspace.entities.users.Student;

@Local
public interface StudentEJBLocal {
	
	
	public void addStudent(Student std);
	public  List<Student> getAll();
	public  List<Student> getAllStudentCreated();
	
	public void login(int cin);
	void acceptPFE(long id);
	public  List<Student> getAllStudentdisabled();
	public  List<Student> getAllStudentNodisabled();
	public  List<Student> getAllStudentCIN();

	void sendMail(String text);
	
	
	

}
