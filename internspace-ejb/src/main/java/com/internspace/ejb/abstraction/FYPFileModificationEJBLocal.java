package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;

@Local
public interface FYPFileModificationEJBLocal {
	
	public FYPFile addFYPSheet(FYPFile file);

}
