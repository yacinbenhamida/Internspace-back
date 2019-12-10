package com.internspace.entities.fyp;

import java.io.Serializable;

import javax.persistence.Table;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity
@Table(name = "file_template_element")
public class FileTemplateElement implements Serializable {

	public enum ElementType {
		title, description, problematic, categories, features, keywords, company, supervisor, rapporteur, universityIcon,
		// Below are InternshipConvention related...
		dateInfo,
		// company

	}

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fypte_id")
	long id;

	@Column(name = "section_name", nullable = true)
	String sectionName;

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	ElementType fileTemplateElementType;

	float x_coord;

	float y_coord;

	float height;

	float weight;

	/*
	 * Associations
	 */

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "file_template_id", insertable = true, updatable = true)
	FileTemplate fileTemplate;

	/*
	 * Construction
	 */

	public FileTemplateElement() {

	}

	public FileTemplateElement(String sectionName, ElementType fileTemplateElementType, FileTemplate fypTemplate) {
		this.sectionName = sectionName;
		this.fileTemplateElementType = fileTemplateElementType;
		this.fileTemplate = fypTemplate; // Required to set file_template_id in current table.

		// ...
	}

	/*
	 * Useful for updating template elements
	 */
	public FileTemplateElement(long id, float x_coord, float y_coord, float height, float weight) {
		super();
		this.id = id;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.height = height;
		this.weight = weight;
	}

	/*
	 * Helpers
	 */

	public void resolveSettings(FileTemplateElement elementToCopy) {
		this.x_coord = elementToCopy.x_coord;
		this.y_coord = elementToCopy.y_coord;
		this.height = elementToCopy.height;
		this.weight = elementToCopy.weight;
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

	public void setFileTemplate(FileTemplate fileTemplate) {
		this.fileTemplate = fileTemplate;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public ElementType getFileTemplateElementType() {
		return fileTemplateElementType;
	}

	public void setFileTemplateElementType(ElementType fileTemplateElementType) {
		this.fileTemplateElementType = fileTemplateElementType;
	}

	public float getX_coord() {
		return x_coord;
	}

	public void setX_coord(float x_coord) {
		this.x_coord = x_coord;
	}

	public float getY_coord() {
		return y_coord;
	}

	public void setY_coord(float y_coord) {
		this.y_coord = y_coord;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	/*
	 * public FileTemplate getFypTemplate() { return fileTemplate; } public void
	 * setFypTemplate(FileTemplate fypTemplate) { this.fileTemplate = fypTemplate; }
	 */

}
