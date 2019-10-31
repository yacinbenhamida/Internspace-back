package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;
import javax.xml.ws.Response;

import com.internspace.entities.university.CompanyCoordinates;

@Local
public interface CompanyCoordinatesEJBLocal {

	public void addCompanyCoordinates(CompanyCoordinates c);
	public void deleteCompanyCoordinates(long id);
	public void updateCompanyCoordinates(CompanyCoordinates c);
	public List<CompanyCoordinates> listeCompanyCoordinates();
	
}
