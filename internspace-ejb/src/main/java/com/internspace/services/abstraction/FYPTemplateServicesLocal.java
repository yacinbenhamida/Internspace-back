package com.internspace.services.abstraction;

import javax.ejb.Local;

import java.util.List;

import com.internspace.entities.FYPTElement;
import com.internspace.entities.FYPTemplate;

@Local
public interface FYPTemplateServicesLocal {
	void createNewTemplate(FYPTemplate newFypTemplate);
	void createNewElement(FYPTElement newFyptElement, FYPTemplate toTemplate);
	List<FYPTemplate> getAll();
}
