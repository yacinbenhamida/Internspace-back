package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.SiteEJBLocal;
import com.internspace.entities.university.Site;
import com.internspace.entities.university.University;

@Stateless
public class SiteEJB implements SiteEJBLocal{
	@PersistenceContext
	EntityManager em;

	@Override
	public int addSite(Site site) {
		System.out.println("addSite: " + site);
		em.persist(site);
		return 1;
	}

	@Override
	public List<Site> getAllSites() {
		return em.createQuery("SELECT s from Site s").getResultList();
	}

	@Override
	public int updateSite(Site site) {
		em.merge(site);
		return 1;
	}

	@Override
	public int deleteSite(long id) {
		Site s = em.find(Site.class, id);
		if(s != null) {
			em.remove(s);
			return 1;
		}
		return 0;
	}

	@Override
	public int setInternshipDirector(long idSite, long idEmployee) {
		Query query = em.createQuery("UPDATE SITE s SET s.internshipDirector_user_id=:idEmployee"
				+ "WHERE s.site_id=:idSite ");
		query.setParameter("idEmployee", idEmployee);
		query.setParameter("idDepartement", idSite);
		int result = query.executeUpdate();
		System.out.println("result:"+result);
		return result;
	}
	
	
}
