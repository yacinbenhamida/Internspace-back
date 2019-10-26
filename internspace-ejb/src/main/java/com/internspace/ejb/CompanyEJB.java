package com.internspace.ejb;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.auth.Subject;

import com.internspace.ejb.abstraction.CompanyEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.StudentFYPSubject;
import com.internspace.entities.fyp.FileTemplateElement.ElementType;
import com.internspace.entities.fyp.StudentFYPSubject.ApplianceStatus;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Student;

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
		//em.persist(company);
		em.persist(em.contains(company) ? company : em.merge(company));
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
		em.persist(em.contains(subject) ? subject : em.merge(subject));
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
		List<FYPSubject> out = null;
		
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
	public StudentFYPSubject getStudentToSubject(long studentId, long subjectId)
	{
		String queryStr = "SELECT SFS FROM " + StudentFYPSubject.class.getName() + " SFS"
				+ " JOIN FETCH SFS.student ST JOIN FETCH SFS.subject SB"
				+ " WHERE"
				+ " ST.id = :studentId"
				+ " AND SB.id = :subjectId"
				//+ " AND SFS.status = :status"
				;
				
		TypedQuery<StudentFYPSubject> query = em.createQuery(queryStr, StudentFYPSubject.class)
				.setParameter("studentId", studentId)
				.setParameter("subjectId", subjectId)
				;
		
		List<StudentFYPSubject> SFSs = query.getResultList();
		
		ApplianceStatus res = ApplianceStatus.none;
		
		if(SFSs != null && SFSs.size() > 0)
			return SFSs.get(0);
		
		return null;
	}
	
	@Override
	public boolean studentToggleAppliance(long studentId, long subjectId) {
		boolean success = tryApplyOnSubject(studentId, subjectId);
		
		if(!success)
			success = tryUnapplyOnSubject(studentId, subjectId);
		
		return success;
	}
	
	@Override
	public boolean tryApplyOnSubject(long studentId, long subjectId) {
		
		ApplianceStatus status = ApplianceStatus.none;
		StudentFYPSubject SFS = getStudentToSubject(studentId, subjectId);
		
		if(SFS != null)
			status = SFS.getApplianceStatus();
		
		EnumSet<ApplianceStatus> canChangeStatus = EnumSet.allOf(ApplianceStatus.class);
				
		canChangeStatus = EnumSet.of(
				ApplianceStatus.none
				// ...
				);
		
		// Means we can change
		if(canChangeStatus.contains(status))
		{
			if(SFS == null) // Create a row
			{
				SFS = new StudentFYPSubject(
					em.find(Student.class, studentId),
					em.find(FYPSubject.class, subjectId)
					);
			}
			
			SFS.setApplianceStatus(ApplianceStatus.pending);
			
			em.persist(SFS);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean tryUnapplyOnSubject(long studentId, long subjectId) {
		
		ApplianceStatus status = ApplianceStatus.none;
		StudentFYPSubject SFS = getStudentToSubject(studentId, subjectId);
		
		if(SFS == null)
			return false;
		
		status = SFS.getApplianceStatus();
		
		EnumSet<ApplianceStatus> canChangeStatus = EnumSet.allOf(ApplianceStatus.class);
				
		canChangeStatus = EnumSet.of(
				ApplianceStatus.pending
				// ...
				);
		
		// Means we can change
		if(canChangeStatus.contains(status))
		{
			SFS.setApplianceStatus(ApplianceStatus.none);
			
			em.persist(SFS);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public List<StudentFYPSubject> getStudentFypSubjectsOfSubjectByStatus(long subjectId, ApplianceStatus status, boolean fetchAll) {
		List<StudentFYPSubject> out = null;
		
		String queryStr = "SELECT SFS FROM " + StudentFYPSubject.class.getName() + " SFS"
				+ " LEFT JOIN FETCH SFS.subject S"
				+ " WHERE S.id = :subjectId"
				;
		
		queryStr = fetchAll ? queryStr : queryStr + " AND SFS.applianceStatus = :status";
		
		TypedQuery<StudentFYPSubject> query = em.createQuery(queryStr, StudentFYPSubject.class)
				.setParameter("subjectId", subjectId)
				;
		
		if(!fetchAll)
			query.setParameter("status", status);

		out = query.getResultList();
		System.out.println("Printing FOUND STUFF");
		out.stream().forEach(e -> System.out.println(e.getId()));
		
		return out;
	}

	@Override
	public List<StudentFYPSubject> getStudentFypSubjectsOfStudentByStatus(long studentId, ApplianceStatus status, boolean fetchAll) {
		List<StudentFYPSubject> out = null;
		
		String queryStr = "SELECT SFS FROM " + StudentFYPSubject.class.getName() + " SFS"
				+ " LEFT JOIN FETCH SFS.student S"
				+ " WHERE S.id = :subjectId"
				;
		
		queryStr = fetchAll ? queryStr : queryStr + " AND SFS.applianceStatus = :status";
		
		TypedQuery<StudentFYPSubject> query = em.createQuery(queryStr, StudentFYPSubject.class)
				.setParameter("subjectId", studentId)
				;
		
		if(!fetchAll)
			query.setParameter("status", status);
		
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
	public List<FYPSubject> getAllSubjects() {
		String queryStr = "SELECT s FROM " + FYPSubject.class.getName() + " s";
		TypedQuery<FYPSubject> query = em.createQuery(queryStr, FYPSubject.class);
		
		return query.getResultList();
	}

	@Override
	public boolean acceptStudentAppliance(long studentId, long subjectId) {
		
		System.out.println("00000");
		
		ApplianceStatus status = ApplianceStatus.none;
		StudentFYPSubject SFS = getStudentToSubject(studentId, subjectId);
		
		if(SFS == null)
			return false;
		
		System.out.println("BBBBBBBBBBBBBB");
		
		
		status = SFS.getApplianceStatus();
		
		EnumSet<ApplianceStatus> canChangeStatus = EnumSet.allOf(ApplianceStatus.class);
				
		canChangeStatus = EnumSet.of(
				ApplianceStatus.pending
				// ...
				);

		System.out.println("111111111");
		
		// Means we can accept
		if(canChangeStatus.contains(status))
		{
			System.out.println("2222222222");
			
			SFS.setApplianceStatus(ApplianceStatus.accepted);
			
			FYPSubject subject = em.find(FYPSubject.class, subjectId);
			
			List<StudentFYPSubject> acceptedSFSs = getStudentFypSubjectsOfSubjectByStatus(subjectId, ApplianceStatus.accepted, false);
			
			int curApplicantsCount = acceptedSFSs != null ? acceptedSFSs.size() : 0;
			int maxApplicants = subject.getMaxApplicants();

			System.out.println("curApplicantsCount: " + curApplicantsCount);
			
			boolean canAccept = curApplicantsCount < maxApplicants;
			
			// This should always be true. front end has to check each subject's accepted
			if(canAccept)
			{
				status = ApplianceStatus.accepted;
				SFS.setApplianceStatus(status);
				em.persist(SFS);
				
				boolean shouldRejectAllOthers = curApplicantsCount + 1 < maxApplicants;
				
				System.out.println("..........: " + SFS.getId());
				
				// Shall we just reject everyone else?
				if(shouldRejectAllOthers)
				{
					String queryStr = "UPDATE " + StudentFYPSubject.class.getName() + "SFS "
							+ " SET SFS.applianceStatus = rejected"
							+ " WHERE SFS.subject.id = :subjectId"
							+ " AND SFS.applianceStatus = pending";
					
					TypedQuery<StudentFYPSubject> query = em.createQuery(queryStr, StudentFYPSubject.class);
					
					int updatedCount = query.executeUpdate();
				}
				
				return true;
			}
			
			return false;
		}
		
		return false;
	}

}
