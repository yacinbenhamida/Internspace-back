package com.internspace.entities.fyp;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

@Entity
@Table(name="file_template_element")
public class FileTemplateElement implements Serializable {
	
	public enum E_FileTemplateElementType {
		title,
		description,
		problematic,
		categories,
		features,
		keywords
	}
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fypte_id")
	long id;
	
	@Column(name="section_name", nullable = true)
	String sectionName;
	
	@Column(name="type")
	E_FileTemplateElementType fileTemplateElementType;
	
	@Column(name="x_coord")
	float x_coord;
	
	@Column(name="y_coord")
	float y_coord;	
	
	@Column(name="height")
	float height;
	
	@Column(name="weight")
	float weight;
	
	/*
	 * Associations
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fyp_template_id")
	FileTemplate fypTemplate;

	/*
	 * Getters & Setters
	 */
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public E_FileTemplateElementType getFileTemplateElementType() {
		return fileTemplateElementType;
	}

	public void setFileTemplateElementType(E_FileTemplateElementType fileTemplateElementType) {
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

	public FileTemplate getFypTemplate() {
		return fypTemplate;
	}

	public void setFypTemplate(FileTemplate fypTemplate) {
		this.fypTemplate = fypTemplate;
	}
	
	
}
