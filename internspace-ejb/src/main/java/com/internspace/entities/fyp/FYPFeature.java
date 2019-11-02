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
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "feature")
public class FYPFeature implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feature_id")
	long id;
	
	/*@OneToMany(mappedBy = "feat", fetch = FetchType.LAZY)
	Set<FYPFile> fypFiles = new HashSet<>();*/

	@Column(name = "content")
	String content = "default";

	/*
	 * Associations
	 */

	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "fyp_file_id")
	FYPFile fypFile;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fyp_file_mod_id")
	FYPFileModification fypMod;
	
	/*
	 * Getters & Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

	
	
}
