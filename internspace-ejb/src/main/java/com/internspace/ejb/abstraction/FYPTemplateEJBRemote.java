package com.internspace.ejb.abstraction;

import javax.ejb.Remote;

import java.util.List;

import com.internspace.entities.FYPTElement;
import com.internspace.entities.FYPTemplate;

@Remote
public interface FYPTemplateEJBRemote {
	void createNewTemplate(FYPTemplate newFypTemplate);
	void createNewElement(FYPTElement newFyptElement, FYPTemplate toTemplate);
	List<FYPTemplate> getAll();
}