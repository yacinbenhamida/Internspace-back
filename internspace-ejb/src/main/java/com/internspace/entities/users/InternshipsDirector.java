package com.internspace.entities.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.internspace.entities.fyp.FileTemplate;

@Entity(name="INTERNSHIPS_DIRECTOR")
public class InternshipsDirector extends Employee{

	private static final long serialVersionUID = 1L;
	
	@OneToOne
	Site site;
	@OneToMany(mappedBy="editor")
	List<FileTemplate> templates;
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public List<FileTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<FileTemplate> templates) {
		this.templates = templates;
	}
	
}
