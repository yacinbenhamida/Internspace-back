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
	boolean deleteSubject(FYPSubject subject);
	boolean deleteSubjectById(long subjectId);
	
	/*
	 * Advanced
 	 */
	
	// Subject appliance
	StudentFYPSubject getStudentToSubject(long studentId, long subjectId);
	boolean studentToggleAppliance(long studentId, long subjectId);
	boolean tryApplyOnSubject(long studentId, long subjectId);
	boolean tryUnapplyOnSubject(long studentId, long subjectId);

	List<StudentFYPSubject> getStudentFypSubjectsOfSubjectByStatus(long subjectId, ApplianceStatus status, boolean fetchAll);
	List<StudentFYPSubject> getStudentFypSubjectsOfStudentByStatus(long studentId, ApplianceStatus status, boolean fetchAll);
	List<FYPCategory> getAllCategories(); // Temporary
	List<FYPCategory> getStudentPreferedCategories(long studentId);
	List<FYPSubject> getSuggestedSubjectsByStudent(long studentId, boolean filterUntaken);
	List<FYPSubject> getSuggestedSubjectsByCategories(List<FYPCategory> categories);
	boolean acceptStudentAppliance(long studentId, long subjectId);
	boolean refuseStudentAppliance(long studentId, long subjectId, String reason);
	//boolean subscribe(String username, int password, String companyName);
	// Returns token
	//String companyLogin(String username, String password);
	
	/*
	 * Quiz Section
	 */
	
	
}
