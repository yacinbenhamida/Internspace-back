package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;

@Local
public interface FYPCategoryEJBLocal {
	public FYPCategory approveCategory(FYPCategory category);
	public void probeCategory(FYPCategory category);
}
