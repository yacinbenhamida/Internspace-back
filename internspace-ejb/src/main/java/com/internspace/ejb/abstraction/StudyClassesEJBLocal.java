package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.university.ClassOption;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.university.UniversitaryYear;

@Local
public interface StudyClassesEJBLocal {
	public StudyClass addClass(StudyClass c,int endYear);
	public List<StudyClass> getClassesOfDepartment(long depId,int endYear);
	public List<ClassOption> getAllClassOptionsOfDept(long depId);
	public List<UniversitaryYear> getRegisteredUniYears();
	public StudyClass getClassById(int id);
}
