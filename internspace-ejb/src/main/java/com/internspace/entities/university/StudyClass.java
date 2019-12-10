package com.internspace.entities.university;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.internspace.entities.users.Student;

/*
 * Defines a set of students studying together, a specific ClassOption.
 * Useful when selecting students by studied class-option type...
 */
@Entity
@Table(name="study_class")
public class StudyClass implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Attributes
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="study_class_id")
	long id;
	
	String name;
	
	@Column(name = "class_year")
	int classYear; // Values like (1, 2, 3, 4, 5, ...), 5 means 5ème année...
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "universitary_year_id")
	UniversitaryYear universitaryYear;
	
	/*
	 * Associations
	 */
	
	@OneToMany(mappedBy = "studyClass")
	Set<Student> students;
	
	@ManyToOne
	@JoinColumn(name = "class_option_id", nullable = false)
	ClassOption classOption;
	
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

	public int getClassYear() {
		return classYear;
	}

	public void setClassYear(int classYear) {
		this.classYear = classYear;
	}


	public UniversitaryYear getUniversitaryYear() {
		return universitaryYear;
	}
	public void setUniversitaryYear(UniversitaryYear universitaryYear) {
		this.universitaryYear = universitaryYear;
	}

	/*
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
*/


	public ClassOption getClassOption() {
		return classOption;
	}

	public void setClassOption(ClassOption classOption) {
		this.classOption = classOption;
	}

}