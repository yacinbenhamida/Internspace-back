package com.internspace.entities.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.internspace.entities.fyp.FYPIntervention;

@Entity(name="Teacher")
public class Teacher extends User{

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "teacher")
	List<FYPIntervention> interventions;
}
