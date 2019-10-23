package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;



@Local
public interface FYPFileEJBLocal {

	
	public void addFYPFile(FYPFile file);
	public  List<FYPFile> getAll();
}
