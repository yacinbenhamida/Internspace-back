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
	List<Company> getAll();
	Company findCompany(long companyId);
	List<Company> findCompaniesByName(String name, int n, boolean useLike);
	void updateCompany(Company company);
	void deleteCompany(Company company);
	void deleteCompanyById(long companyId);
	
	/*
	 * CRUD FYPSubject
	 */
	void createSubject(FYPSubject subject);
	List<FYPSubject> getAllSubjects();
	List<FYPSubject> getFypSubjectsByCompany(long companyId, boolean filterUntaken);
	FYPSubject findSubject(long subjectId);
	void updateSubject(FYPSubject subject);
	void deleteSubject(FYPSubject subject);
	void deleteSubjectById(long subjectId);
	
	/*
	 * Advanced
 	 */
	List<FYPSubject> getSuggestedSubjectsByStudent(long studentId, boolean filterUntaken);
	List<FYPSubject> getSuggestedSubjectsByCategories(List<FYPCategory> categories);
	List<StudentFYPSubject> getStudentFypSubjectsByStatus(ApplianceStatus status, boolean fetchAll);
	boolean tryApplyOnSubject(FYPSubject subject, long studentId);
	//void acceptStudentAppliance(FYPSubject subject, long studentId);
	//boolean subscribe(String username, int password, String companyName);
	// Returns token
	//String companyLogin(String username, String password);
	
	/*
	 * Quiz Section
	 */
	
	
}
