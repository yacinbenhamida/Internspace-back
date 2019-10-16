package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.InternshipsDirector;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

/*
 * La fiche PFE (Titre, description, problématique, fonctionnalités, catégorie, mots clé, entreprise). 
 * Exemple de catégorie : .NET, nodeJS, devops, JavaEE. 
 * Une fiche PFE peut appartenir à plusieurs catégories. 
 */

@XmlRootElement
@Entity
@Table(name="fyp_template")
public class FileTemplate implements Serializable {

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
	@Column(name="is_fyp")
	boolean isFyp;	
	
	/*
	 * Associations
	 */
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="fypTemplate", fetch = FetchType.EAGER)
	List<FileTemplateElement> fyptElements;
	@Column(name="internship_director_id")
	@ManyToOne
	InternshipsDirector editor;
	
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

	public List<FileTemplateElement> getFyptElements() {
		return fyptElements;
	}

	public void setFyptElements(List<FileTemplateElement> fyptElements) {
		this.fyptElements = fyptElements;
	}

	public boolean isFyp() {
		return isFyp;
	}

	public void setFyp(boolean isFyp) {
		this.isFyp = isFyp;
	}

	public InternshipsDirector getEditor() {
		return editor;
	}

	public void setEditor(InternshipsDirector editor) {
		this.editor = editor;
	}
	
	

}
