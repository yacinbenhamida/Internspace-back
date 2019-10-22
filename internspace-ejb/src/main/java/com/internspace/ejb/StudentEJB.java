package com.internspace.ejb;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
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

	@Override
	public void enregistrer(long id) {
		
		Student s = em.find(Student.class, id);
		s.setIsCreated(true);
		em.persist(s);
		em.flush();
		
		
		
		
	}

	@Override
	public List<Student> getAllStudentCreated(boolean isCreated) {
		
		return em.createQuery("SELECT s from Student s  where s.is_created=:true").setParameter("isCreated", isCreated).getResultList();
	}

	@Override
	public void acceptPFE(long id) {
	
		Student s= em.find(Student.class, id);
		s.setIsDisabled(true);
		em.persist(s);
		em.flush();
		
	}
	


	/*@Override
	public void sendMail(int year, String text) {
		String subject = "rappel pour saisir votre fiche PFE " ;
		Mailer mail = new Mailer();
		List<Student> ls = getLateStudentsList(year);
		ls.forEach(x->mail.send(x.getEmail(),text,subject));
	}*/
	
	
}
