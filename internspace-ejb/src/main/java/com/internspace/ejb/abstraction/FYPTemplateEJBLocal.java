package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import java.util.List;

import com.internspace.entities.FYPTElement;
import com.internspace.entities.FYPTemplate;

@Local
public interface FYPTemplateEJBLocal {
	void createNewTemplate(FYPTemplate newFypTemplate);
	void createNewElement(FYPTElement newFyptElement, FYPTemplate toTemplate);
	List<FYPTemplate> getAll();
}
