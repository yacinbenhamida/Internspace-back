package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFile;

@Local
public interface TeacherEJBLocal {
	
	List<FYPFile> getPendingFYPFiles();
	boolean PrevalidateFYPFile(FYPFile file);
	List<FYPFile> getSupervisedFYPfiles(long id);

	List<FYPFile> getprotractoredFYPfiles(long id);
	FYPFile ValidateMajorModification(FYPFile f);
	void ProposeFYPCategory(FYPCategory F);
	
	
	
	
	

}
