package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPTemplateEJBLocal;
import com.internspace.ejb.abstraction.FYPTemplateEJBRemote;
import com.internspace.entities.FYPTElement;
import com.internspace.entities.FYPTemplate;

@Stateless
public class FYPTemplateEJB implements FYPTemplateEJBLocal, FYPTemplateEJBRemote {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void createNewTemplate(FYPTemplate newFypTemplate) {
		System.out.println("Adding: " + newFypTemplate);
		
		em.persist(newFypTemplate);
	}
	
	@Override
	public void createNewElement(FYPTElement newFyptElement, FYPTemplate toTemplate) {
		System.out.println("Adding: " + newFyptElement + " to " + toTemplate);
		
		toTemplate.getFyptElements().add(newFyptElement);
		em.persist(toTemplate);
	}

	@Override
	public List<FYPTemplate> getAll() {
		System.out.println("Finding all FYP Templates...");
		List<FYPTemplate> fypTemplates = em.createQuery("from FYPTemplate", FYPTemplate.class).getResultList();
		return fypTemplates;
	}

	
}
