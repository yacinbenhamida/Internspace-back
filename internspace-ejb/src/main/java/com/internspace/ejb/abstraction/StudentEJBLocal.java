package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;


import com.internspace.entities.users.Student;

@Local
public interface StudentEJBLocal {
	
	
	public void addStudent(Student std);
	public  List<Student> getAll();
	public  List<Student> getAllStudentCreated(boolean isCreated);
	public void enregistrer(long id);
	void acceptPFE(long id);
	
	

}
