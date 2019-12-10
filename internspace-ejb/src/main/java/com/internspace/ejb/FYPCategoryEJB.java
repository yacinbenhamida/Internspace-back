package com.internspace.ejb;

import java.util.List;
import java.util.Locale.Category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPCategoryEJBLocal;
import com.internspace.entities.fyp.FYPCategory;
@Stateless
public class FYPCategoryEJB implements FYPCategoryEJBLocal{
	@PersistenceContext
	EntityManager em;
	@Override
	public FYPCategory approveCategory(long id) {
		FYPCategory category = em.find(FYPCategory.class, id);
		category.setApproved(true);
		em.merge(category);
		return em.find(FYPCategory.class, id) ;
	}

	@Override
	public FYPCategory suggestCategory(FYPCategory category) {
		category.setApproved(false);
		em.persist(category);
		return em.find(FYPCategory.class, category.getId()) ;
	}

	@Override
	public boolean deleteCategory(long id) {
		FYPCategory category = em.find(FYPCategory.class, id);
		if (category != null) {
			em.remove(category);
			return true;
		}
		return false;
	}

	@Override
	public FYPCategory addCategory(FYPCategory category) {
		em.persist(category);
		return em.find(FYPCategory.class, category.getId()) ;
	}

	@Override
	public FYPCategory editCategory(FYPCategory category) {
		em.merge(category);
		return em.find(FYPCategory.class, category.getId()) ;
	}

	@Override
	public List<FYPCategory> getSuggestedCategories(long idDep) {
		Query query = em.createQuery("SELECT c from FYPCategory c where c.department.id = :id AND c.isApproved=0").setParameter("id", idDep);
		return query.getResultList();
	}

	@Override
	public List<FYPCategory> getAllCategories(long idDep) {
		return em.createQuery("SELECT c from FYPCategory c where c.department.id = :id")
				.setParameter("id", idDep).getResultList();
	}

}
