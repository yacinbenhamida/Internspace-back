package com.internspace.ejb.abstraction;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Student;

@Local
public interface DashboardEJBLocal {
	/*
	 * Dashboard STATS
	 */
	List<Student> getStudentsBySite(long siteId);
	float getStudentsLocalAbroadDistribution(long uniId, boolean abroad);
	Map<Long, Float> getAbroadPercentagePerYear(long uniId);
	float getStudentsDistributionByLocationAndUY(long uniId, String location, long uyId);
	List<Company> getMostCompanyAcceptingInternsWithUniversity(long uniId, int n);
	List<FYPSubject> getInternshipsByCategory(long uniId, long categoryId);
	List<FYPCategory> getMostRequestedCategoriesByCompanies();
	List<FYPCategory> getMostRequestedCategoriesByStudentsOfUni(long uniId);
	Map<Long, List<FYPSubject>> getInternshipEvolutionPerUYByCategory(long uniId, long categoryId);
	Map<String, List<Student>> getStudentInternshipPerCountry(long uniId);
	
	// Helpers
	
	List<FYPCategory> getCategories();
	List<UniversitaryYear> getUniversitaryYears();
	
}
