package com.internspace.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.GET;

import com.internspace.ejb.abstraction.FYPTemplateEJBLocal;
import com.internspace.entities.FYPTElement;
import com.internspace.entities.FYPTemplate;

@Path("template")
@Stateless
public class FYPTemplateService {

	@EJB
	FYPTemplateEJBLocal ejb;
	
	public void createNewTemplate(FYPTemplate newFypTemplate) {
		System.out.println("Adding: " + newFypTemplate);
		ejb.createNewTemplate(newFypTemplate);
	}

	public void createNewElement(FYPTElement newFyptElement, FYPTemplate toTemplate) {
		ejb.createNewElement(newFyptElement, toTemplate);
	}

	@GET
	public String debugString()
	{
		return "Rest is working!";
	}
	
	public List<FYPTemplate> getAll() {
		return ejb.getAll();
	}

}
