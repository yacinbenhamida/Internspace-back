package com.internspace.entities.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.university.Departement;

@Entity(name="Teacher")
public class Teacher extends User{

	private static final long serialVersionUID = 1L;

	/*
	 * Associations
	 */
	
	@OneToMany(mappedBy = "teacher")
	List<FYPIntervention> interventions;
	
	@ManyToOne
	Departement departement;
}
