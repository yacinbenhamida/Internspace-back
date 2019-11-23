package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "fyp_defense")
public class FYPDefense implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fyp_defense_id")
	long id;
	
	@Column(name = "classroom")
	String classroom;

	@Temporal(TemporalType.TIMESTAMP)
	Date defenseDate;
	
	@OneToOne
	@JoinColumn(name = "fyp_file_id")
	private FYPFile fyp_file_defense;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDefenseDate() {
		return defenseDate;
	}

	public void setDefenseDate(Date defenseDate) {
		this.defenseDate = defenseDate;
	}

	public FYPFile getFyp_file_defense() {
		return fyp_file_defense;
	}

	public void setFyp_file_defense(FYPFile fyp_file_defense) {
		this.fyp_file_defense = fyp_file_defense;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	
	
	
}
