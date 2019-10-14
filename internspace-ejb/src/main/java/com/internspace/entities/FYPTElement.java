package com.internspace.entities;

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
@Table(name="fypt_element")
public class FYPTElement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fypte_id")
	long id;
	
	@Column(name="section_name")
	String sectionName;
	
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
	FYPTemplate fypTemplate;
}
