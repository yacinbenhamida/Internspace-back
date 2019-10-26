package com.internspace.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.internspace.ejb.abstraction.CompanyEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.fyp.StudentFYPSubject.ApplianceStatus;
import com.internspace.entities.users.Company;

@Stateless
public class CompanyEJB implements CompanyEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	// Company Section

	@Override
	public void createCompany(Company company) {
		System.out.println("Adding: " + company);
		em.persist(company);
	}

	@Override
	public List<Company> getAll() {
		return  em.createQuery("SELECT c from Company c", Company.class).getResultList();
	}
	
	@Override
	public Company findCompany(long id) {
		return em.find(Company.class, id);
	}

	@Override
	public List<Company> findCompaniesByName(String name, int n, boolean useLike)
	{
		String nameMatching;
		nameMatching = useLike ? "LIKE '%" + name.toLowerCase() + "%'" : "= '" + name.toLowerCase() + "'";
		
		String queryStr = "SELECT DISTINCT C FROM " + Company.class.getName() + " C"
				+ " WHERE lower(C.name) " + nameMatching;

		List<Company> companies = em.createQuery(queryStr, Company.class).setMaxResults(n).getResultList();
		
		return companies;
		
	}
	
	@Override
	public void updateCompany(Company company) {
		System.out.println("Updating: " + company);
		em.persist(company);
	}

	@Override
	public void deleteCompanyById(long companyId) {
		Company company = findCompany(companyId);
		em.remove(company);
	}

	@Override
	public void deleteCompany(Company company) {
		em.remove(em.contains(company) ? company : em.merge(company));
	}
	
	// Subjects Section
	
	@Override
	public void createSubject(FYPSubject subject) {
		System.out.println("Adding: " + subject);
		em.persist(subject);
	}

	@Override
	public FYPSubject findSubject(long subjectId)
	{
		return em.find(FYPSubject.class, subjectId);
	}
	
	@Override
	public void updateSubject(FYPSubject subject) {
		System.out.println("Updating: " + subject);
		em.persist(subject);
	}

	@Override
	public void deleteSubjectById(long subjectId) {
		FYPSubject subject = findSubject(subjectId);
		em.remove(subject);
	}

	@Override
	public void deleteSubject(FYPSubject subject) {
		em.remove(em.contains(subject) ? subject : em.merge(subject));
	}
	
	@Override
	public List<FYPSubject> getFypSubjectsByCompany(long companyId, boolean filterUntaken) {
		List<FYPSubject> out = new ArrayList<FYPSubject>();
		
		String queryStr = "SELECT s FROM " + FYPSubject.class.getName() + " s"
				+ " LEFT JOIN FETCH s.studentSubjects ss"
				+ " WHERE"
				//+ " s.id = ss.id"
				+ " s.company.id = :companyId";
		
		System.out.println("Company ID: " + companyId + " | Does Filter?: " + filterUntaken);
		
		queryStr = filterUntaken ? queryStr + 
				" AND (ss.applianceStatus = NULL OR ss.applianceStatus <> :status)" : queryStr;
		
		TypedQuery<FYPSubject> query = em.createQuery(queryStr, FYPSubject.class)
				.setParameter("companyId", companyId);
		
		query = filterUntaken ? query.setParameter("status", StudentFYPSubject.ApplianceStatus.accepted) : query;
		
		out = query.getResultList();
		
		return out;
	}

	@Override
	public List<FYPSubject> getSuggestedSubjectsByStudent(long studentId, boolean filterUntaken) {
		return null;
	}

	@Override
	public List<FYPSubject> getSuggestedSubjectsByCategories(List<FYPCategory> categories) {
		return null;
	}

	@Override
	public List<StudentFYPSubject> getStudentFypSubjectsByStatus(ApplianceStatus status, boolean fetchAll) {
		return null;
	}

	@Override
	public boolean tryApplyOnSubject(FYPSubject subject, long studentId) {
		return false;
	}

	@Override
	public List<FYPSubject> getAllSubjects() {
		String queryStr = "SELECT s FROM " + FYPSubject.class.getName() + " s";
		TypedQuery<FYPSubject> query = em.createQuery(queryStr, FYPSubject.class);
		
		return query.getResultList();
	}
}
