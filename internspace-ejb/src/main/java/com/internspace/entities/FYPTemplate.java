package com.internspace.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Table(name="fyp_template")
public class FYPTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fypt_id")
	long id;
	
	@Column(name="template_name")
	String templateName = "template_" + id;
	
	/*
	 * Associations
	 */
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="fypTemplate")
	List<FYPTElement> fyptElements;

	
	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<FYPTElement> getFyptElements() {
		return fyptElements;
	}

	public void setFyptElements(List<FYPTElement> fyptElements) {
		this.fyptElements = fyptElements;
	}

}
