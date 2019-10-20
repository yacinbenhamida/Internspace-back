package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;

@Local
public interface FYPCategoryEJBLocal {
	public FYPCategory approveCategory(FYPCategory category);
	public FYPCategory suggestCategory(FYPCategory category);
	public boolean deleteCategory(FYPCategory category);
	public FYPCategory addCategory(FYPCategory category);
	public FYPCategory editCategory(FYPCategory category);
}
