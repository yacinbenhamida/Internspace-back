package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.FileTemplateElement;

import java.util.List;

@Local
public interface FileTemplateEJBLocal {
	/*
	 * Template CRUD
	 */
	void createTemplate(FileTemplate template);
	List<FileTemplate> getAllTemplates();
	
	/*
	 * Template Elements' CRUD
	 */
	void createElement(FileTemplateElement element);
	void updateElement(FileTemplateElement element);
}
