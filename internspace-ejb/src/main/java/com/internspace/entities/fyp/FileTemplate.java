package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.internspace.entities.users.Employee;

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

import com.internspace.entities.fyp.FileTemplateElement.ElementType;

@XmlRootElement
@Entity
@Table(name="file_template")
public class FileTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="file_template_id")
	long id;
	
	@Column(name="template_name")
	String templateName = "template_" + id;
	@Column(name="is_fyp")
	boolean isFyp;	

	/*
	 * Associations
	 */	
	
	@OneToMany(mappedBy="fileTemplate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<FileTemplateElement> templateElements = new ArrayList<FileTemplateElement>();
	
	// Explicitly check if this employee has InternshipsDirector role.
	@ManyToOne
	Employee editor;
		
	/*
	 * Construction
	 */
	public FileTemplate()
	{
		
	}
	
	public FileTemplate (String templateName, boolean isFyp, Employee editor)
	{	
		this.templateName = templateName;
		this.isFyp = isFyp;
		this.editor = editor;
		
		EnumSet<ElementType> elemTypes = EnumSet.allOf(ElementType.class);
		if (isFyp) // We're creating a final-year-project file template.
		{
			elemTypes = EnumSet.of(
					ElementType.title,
					ElementType.description,
					ElementType.problematic,
					ElementType.features,
					ElementType.categories,
					ElementType.keywords,
					ElementType.company,
					ElementType.supervisor,
					ElementType.rapporteur,
					ElementType.universityIcon
					// ...
					);
		} else {
			elemTypes = EnumSet.of(
					ElementType.dateInfo
					// ...
					);	
		}
		
		for (ElementType elemType : elemTypes) {
			FileTemplateElement fypTemplateElement = new FileTemplateElement(elemType.name(), elemType, this);
			this.templateElements.add(fypTemplateElement);
			System.out.print(elemType.name());
		}	
	}
	
	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTemplateName()
	{
		return this.templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<FileTemplateElement> getTemplateElements() {
		return templateElements;
	}

	public void setTemplateElements(List<FileTemplateElement> templateElements) {
		this.templateElements = templateElements;
	}

	public void setXFyptElements(List<FileTemplateElement> fyptElements) {
		this.templateElements = fyptElements;
	}

	public void setIsFyp(boolean isFyp) {
		this.isFyp = isFyp;
	}

	/*
	public Employee getEditor() {
		return this.editor;
	}
	*/
	public void setEditor(Employee editor) {
		this.editor = editor;
	}

}
