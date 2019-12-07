package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPCategory;

@Local
public interface FYPCategoryEJBLocal {
	public FYPCategory approveCategory(long id);
	public FYPCategory suggestCategory(FYPCategory category);
	public boolean deleteCategory(long id);
	public FYPCategory addCategory(FYPCategory category);
	public FYPCategory editCategory(FYPCategory category);
	public List<FYPCategory> getSuggestedCategories(long idDep);
	public List<FYPCategory> getAllCategories(long idDep);
}
