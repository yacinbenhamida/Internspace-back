package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.fyp.StudentFYPSubject.ApplianceStatus;
import com.internspace.entities.users.Company;

@Local
public interface CompanyEJBLocal {
	/*
	 * Company & FYPSubject Services
	 */
	
	/*
	 * CRUD Company
	 */
	void createCompany(Company company);
	void updateCompany(Company company);
	void deleteCompany(Company company);
	
	/*
	 * CRUD FYPSubject
	 */
	void createSubject(FYPSubject subject);
	void updateSubject(FYPSubject subject);
	void deleteSubject(FYPSubject subject);
	
	/*
	 * Advanced
 	 */
	List<FYPSubject> getFypSubjectsByCompany(long companyId, boolean filterUntaken);
	List<FYPSubject> getSuggestedSubjectsByStudent(long studentId, boolean filterUntaken);
	List<FYPSubject> getSuggestedSubjectsByCategories(List<FYPCategory> categories);
	List<StudentFYPSubject> getStudentFypSubjectsByStatus(ApplianceStatus status, boolean fetchAll);
	boolean tryApplyOnSubject(FYPSubject subject, long studentId);
	
	/*
	 * Quiz Section
	 */
	
	
}
