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
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.fyp.StudentFYPSubject.ApplianceStatus;
import com.internspace.entities.users.Company;

@Stateless
public class CompanyEJB implements CompanyEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void createCompany(Company company) {
		System.out.println("Adding: " + company);
		em.persist(company);
	}

	@Override
	public void updateCompany(Company company) {
		System.out.println("Updating: " + company);
		em.persist(company);
	}

	@Override
	public void deleteCompany(Company company) {
		em.remove(company);
	}

	@Override
	public void createSubject(FYPSubject subject) {
		System.out.println("Adding: " + subject);
		em.persist(subject);
	}

	@Override
	public void updateSubject(FYPSubject subject) {
		System.out.println("Updating: " + subject);
		em.persist(subject);
	}

	@Override
	public void deleteSubject(FYPSubject subject) {
		em.remove(subject);
	}

	@Override
	public List<FYPSubject> getFypSubjectsByCompany(long companyId, boolean filterUntaken) {
		List<FYPSubject> out = new ArrayList<FYPSubject>();
		
		String queryStr = "SELECT s FROM " + FYPSubject.class.getName() + " s "
				+ " LEFT JOIN s.studentSubjects ss"
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FYPSubject> getSuggestedSubjectsByCategories(List<FYPCategory> categories) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentFYPSubject> getStudentFypSubjectsByStatus(ApplianceStatus status, boolean fetchAll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryApplyOnSubject(FYPSubject subject, long studentId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Company> getAll() {
		return  em.createQuery("SELECT c from Company c ").getResultList();
	}

	
}
