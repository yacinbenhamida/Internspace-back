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
	public List<Student> getAllStudentCreated() {
		
		return em.createQuery("SELECT s from Student s  where s.isCreated=:isCreated").setParameter("isCreated", true).getResultList();
	}

	@Override
	public void acceptPFE(long id) {
	
		Student s= em.find(Student.class, id);
		if (s.getIsCreated()==true) {
		s.setIsDisabled(true);
		em.persist(s);
		em.flush();}
		else
		{
			System.out.println("this student is not created");
		}
		
	}

	@Override
	public List<Student> getAllStudentdisabled() {
		return em.createQuery("SELECT s from Student s  where s.isDisabled=:isDisabled").setParameter("isDisabled", true).getResultList();
		
	}

	@Override
	public List<Student> getAllStudentNodisabled() {
		return em.createQuery("SELECT s from Student s  where s.isDisabled=:isDisabled").setParameter("isDisabled", false).getResultList();
		
	}
	
	
	@Override
	public void sendMail(String text) {
		
		String subject = "Voici c votre mot de passe " ;
		String subject1 = "Vous êtes pas autorisé a paser le PFE " ;
		
		Mailer mail = new Mailer();
		List<Student> ls = getAllStudentdisabled();
		List<Student> ls1 = getAllStudentNodisabled();
		
		
		
		ls.forEach(x->mail.send(x.getEmail(),text,subject));
		ls1.forEach(x->mail.send(x.getEmail(),text,subject1));
		
	}



	/*@Override
	public void sendMail(int year, String text) {
		String subject = "rappel pour saisir votre fiche PFE " ;
		Mailer mail = new Mailer();
		List<Student> ls = getLateStudentsList(year);
		ls.forEach(x->mail.send(x.getEmail(),text,subject));
	}*/
	
	
}
