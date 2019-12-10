package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileModification;
import com.internspace.entities.users.Student;

@Local
public interface FYPFileModificationEJBLocal {
	
	public FYPFile addFYPSheet(FYPFile file);
	public  List<FYPFile> getAllFilesModification();
	public  List<FYPFileModification> getAll();
	public FYPFile editFYPSheet(FYPFile file);
	public boolean acceptModification(long id);
	
	

}
