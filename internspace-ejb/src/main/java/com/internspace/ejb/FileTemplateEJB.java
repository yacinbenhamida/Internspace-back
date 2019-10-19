package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.FileTemplateElement;
import com.internspace.entities.users.Employee;

@Stateless
public class FileTemplateEJB implements FileTemplateEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public void createTemplate(FileTemplate template) {
		System.out.println("Adding: " + template);
		// System.out.println("Template Elements count: " + template.getFyptElements().size());	
		
		em.persist(template);
		//em.flush();
	}
	
	@Override
	public void updateTemplateEditor(FileTemplate template, Employee editor) {
		System.out.println("Adding: " + template);
		// System.out.println("Template Elements count: " + template.getFyptElements().size());	
		
		template = em.find(FileTemplate.class, template.getId());
		template.setEditor(editor);
	}
	
	@Deprecated
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
		return fypTemplates;
	}
}
