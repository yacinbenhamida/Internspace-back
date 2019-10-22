package com.internspace.ejb.abstraction;

import java.util.List;

import com.internspace.entities.university.Site;


public interface SiteEJBLocal {
	int addSite(Site site);
	public List<Site> getAllSites();
	int updateSite(Site site);
	int deleteSite(long id);
}
