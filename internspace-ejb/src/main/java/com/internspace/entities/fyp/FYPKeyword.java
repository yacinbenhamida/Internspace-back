package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "keyword")
public class FYPKeyword implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "keyword_id")
	long id;

	@Column(name = "name")
	String name = "default";

	/*
	 * Associations
	 */
	
	@ManyToOne
	@JoinColumn(name = "fyp_file_id")
	FYPFile fypFile;

	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
