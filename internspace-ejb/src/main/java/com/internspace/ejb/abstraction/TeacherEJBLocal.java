package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileModification;

@Local
public interface TeacherEJBLocal {
	 List<FYPFileModification>getAllFypmodification();
	List<FYPFile> getPendingFYPFiles();
	FYPFile PrevalidateFYPFile(long  file);
	List<FYPFile> getSupervisedFYPfiles(long id);

	List<FYPFile> getprotractoredFYPfiles(long id);
	List<FYPCategory> getAllCategories();
	void ValidateMajorModification(long  f,long dd);
	void ProposeFYPCategory(FYPCategory F);
	List<FYPFile> getPrevalidatedFiles(long id);
	
	
	
	
	
	

}
