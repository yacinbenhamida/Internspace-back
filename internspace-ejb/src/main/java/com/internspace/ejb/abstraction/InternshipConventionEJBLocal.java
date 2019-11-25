package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;


import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.users.Student;

@Local
public interface InternshipConventionEJBLocal {
	
	public void addInternshipConvention(InternshipConvention inter, long id);
	public  List<InternshipConvention> getAllInternshipConvention();
	public int removeConvention(long  id);
	public List<Student> getFypConventionStudent(long id);

}
