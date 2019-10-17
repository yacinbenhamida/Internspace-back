package com.internspace.entities.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="SITE")
public class Site implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@OneToOne
	 InternshipsDirector internshipsDirector;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public InternshipsDirector getInternshipsDirector() {
		return internshipsDirector;
	}

	public void setInternshipsDirector(InternshipsDirector internshipsDirector) {
		this.internshipsDirector = internshipsDirector;
	}
	
	
}
