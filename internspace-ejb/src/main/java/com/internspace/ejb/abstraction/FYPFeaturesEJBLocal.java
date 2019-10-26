package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;


@Local
public interface FYPFeaturesEJBLocal {
	
	
	public List<FYPFeature>ListFYPFeature();
	public void addFYPFeature(FYPFeature file);
	

}
