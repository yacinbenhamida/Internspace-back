package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FileTemplateEJBLocal;
import com.internspace.entities.fyp.FYPFile;
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
	public boolean updateTemplateEditor(FileTemplate template, Employee editor) {
		System.out.println("Adding: " + template);
		// System.out.println("Template Elements count: " + template.getFyptElements().size());	
		
		template = em.find(FileTemplate.class, template.getId());
		
		if(template == null)
			return false;
		
		template.setEditor(editor);
		em.persist(template);
		
		return true;
	}
	
	@Override
	public void removeTemplate(long id)
	{
		FileTemplate fileTemplate = findTemplate(id);
		
	    if (fileTemplate != null) {
	      em.remove(fileTemplate);
	    }
	}
	
	@Override
	public FileTemplate findTemplate(long id)
	{
		return em.find(FileTemplate.class, id);
	}
	
	@Override
	public List<FileTemplate> findTemplatesByName(String name, int n, boolean useLike)
	{
		String nameMatching;
		nameMatching = useLike ? "LIKE '%" + name.toLowerCase() + "%'" : "= '" + name.toLowerCase() + "'";
		
		String queryStr = "SELECT DISTINCT FT FROM " + FileTemplate.class.getName() + " FT"
				+ " JOIN FETCH FT.templateElements FTE"
				+ " WHERE lower(FT.templateName) " + nameMatching;

		List<FileTemplate> templates = em.createQuery(queryStr, FileTemplate.class).setMaxResults(n).getResultList();
		
		templates.stream().forEach(e -> System.out.println(e));
		
		return templates;
		
	}
	
	@Override
	public List<FYPFile> findFypFileByName(String name, int n, boolean useLike)
	{
		if(name == "") return null;
		
		System.out.println("name: " + name + " | n: " + n + " | useLike: " + (useLike ? "true" : "false") + ".");
		
		String nameMatching;
		nameMatching = useLike ? "LIKE '%" + name.toLowerCase() + "%'" : "= '" + name.toLowerCase() + "'";
		
		String queryStr = "SELECT DISTINCT FF FROM " + FYPFile.class.getName() + " FF"
				// + " JOIN FETCH FF.features FT"
				+ " WHERE lower(FF.title) " + nameMatching;

		List<FYPFile> files = em.createQuery(queryStr, FYPFile.class).setMaxResults(n).getResultList();
		
		files.stream().forEach(e -> System.out.println(e));
		
		return files;
	}
	
	
	@Override
	public List<FileTemplate> findTemplateVersionsByName(String name)
	{	
		String queryStr = "SELECT DISTINCT FT FROM " + FileTemplate.class.getName() + " FT"
				+ " JOIN FETCH FT.templateElements FTE"
				+ " WHERE lower(FT.templateName) LIKE '" + name.toLowerCase() + "%'";

		List<FileTemplate> templates = em.createQuery(queryStr, FileTemplate.class)
				//.setParameter("name", name)
				.getResultList();
		
		templates.stream().forEach(e -> System.out.println(e.getTemplateName()));
		
		return templates;
	}
	
	@Deprecated
	@Override
	public void createElement(FileTemplateElement element) {
		//System.out.println("Adding: " + element + " to " + element.getFypTemplate());

		em.persist(element);
	}

	@Override
	public boolean updateElement(FileTemplateElement element) {
		System.out.println("Updating: " + element);
		
		FileTemplateElement templateElement = em.find(FileTemplateElement.class, element.getId());
		if(templateElement == null)
			return false;
		
		templateElement.resolveSettings(element);
		
		em.persist(templateElement);
		
		return true;
	}

	@Override
	public List<FileTemplate> getAllTemplates() {
		System.out.println("Finding all FYP Templates...");
		List<FileTemplate> fypTemplates = em.createQuery("SELECT distinct T from " + FileTemplate.class.getName() + " T"
				+ " JOIN FETCH T.templateElements TE"
				, FileTemplate.class)
				.getResultList();
		return fypTemplates;
	}
	
	@Override
	public List<FileTemplate> getAllByEditor(long editorId) {
		System.out.println("Finding all FYP Templates for editor...");
		List<FileTemplate> fypTemplates = em.createQuery("SELECT distinct T from " + FileTemplate.class.getName() + " T"
				+ " JOIN FETCH T.templateElements TE"
				+ " JOIN FETCH T.editor E"
				+ " WHERE E.id = :editorId"
				, FileTemplate.class)
				.setParameter("editorId", editorId)
				.getResultList();
		return fypTemplates;
	}
}
