package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.university.University;

@Local
public interface UniversityEJBLocal {
	/**
	 * University CRUD
	 */
	int addUniversity(University university);
	public List<University> getAllUniversities();
	int updateUniversity(University university);
	int deleteUniversity(long id);
	University searchUniversity(long id);
	
}
