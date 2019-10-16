package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.FileTemplateElement;

@Stateless
public class FileTemplateEJB implements FileTemplateEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void createTemplate(FileTemplate template) {
		System.out.println("Adding: " + template);
		
		em.persist(template);
	}
	
	@Override
	public void createElement(FileTemplateElement element) {
		System.out.println("Adding: " + element + " to " + element.getFypTemplate());

		em.persist(element);
	}

	@Override
	public void updateElement(FileTemplateElement element) {
		System.out.println("Updating: " + element);
		
		em.persist(element);
	}

	@Override
	public List<FileTemplate> getAllTemplates() {
		System.out.println("Finding all FYP Templates...");
		List<FileTemplate> fypTemplates = em.createQuery("from " + FileTemplate.class.getName(), FileTemplate.class).getResultList();
		for(int i=0; i < fypTemplates.size(); i++)
		{
			System.out.println(fypTemplates.get(i).getTemplateName());
		}
		return fypTemplates;
	}
}
