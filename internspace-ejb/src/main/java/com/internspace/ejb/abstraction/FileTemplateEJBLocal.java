package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.FileTemplateElement;
import com.internspace.entities.users.Employee;

import java.util.List;

@Local
public interface FileTemplateEJBLocal {
	/*
	 * Template CRUD
	 */
	void createTemplate(FileTemplate template);
	boolean updateTemplateEditor(FileTemplate template, Employee editor);
	void removeTemplate(long id);
	FileTemplate findTemplate(long id);
	List<FileTemplate> findTemplatesByName(String name, int n, boolean useLike);
	List<FYPFile> findFypFileByName(String name, int n, boolean useLike);
	List<FileTemplate> findTemplateVersionsByName(String name);
	List<FileTemplate> getAllTemplates();
	List<FileTemplate> getAllByEditor(long editorId);

	
	/*
	 * Template Elements' CRUD
	 */
	void createElement(FileTemplateElement element);
	boolean updateElement(FileTemplateElement element);
	
	/*
	 * Advanced
	 */
	// Random template generation
}
