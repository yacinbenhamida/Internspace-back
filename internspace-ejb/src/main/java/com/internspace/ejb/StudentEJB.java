package com.internspace.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.StudentEJBLocal;

import com.internspace.entities.users.Student;

public class StudentEJB implements StudentEJBLocal{

	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void addStudent(Student std) {
		
		System.out.println("Adding: " + std);
		em.persist(std);
		
		
	}

	@Override
	public List<Student> getAll() {
		return em.createQuery("SELECT c from Student c").getResultList();
		
	}
	
	
}
