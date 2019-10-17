package com.internspace.entities.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.internspace.entities.fyp.FYPIntervention;

@Entity(name="Teacher")
public class Teacher extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@OneToMany(mappedBy = "teacher")
	List<FYPIntervention> interventions;
}
