package com.internspace.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.MailerStudent;
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
	public List<Student> getAllStudentSaved() {
		
		return em.createQuery("SELECT s from Student s  where s.isSaved=:isSaved").setParameter("isSaved", true).getResultList();
	}
	

	

	@Override
	public List<Student> getAllStudentdisabled() {
		return em.createQuery("SELECT s from Student s  where s.isAutorised=:isAutorised").setParameter("isAutorised", true).getResultList();
		
	}

	@Override
	public List<Student> getAllStudentNodisabled() {
		return em.createQuery("SELECT s from Student s  where s.isAutorised=:isAutorised").setParameter("isAutorised", false).getResultList();
		
	}
	
	
	@Override
	public void sendMail(String text,String cin) {
		
		String subject = "Voici c votre mot de passe " ;
		String subject1 = "Vous êtes pas autorisé a paser le PFE " ;
		
		MailerStudent mail = new MailerStudent();
		List<Student> ls = getAllStudentdisabled();
		List<Student> ls1 = getAllStudentNodisabled();
		List<Student> ls2 = getAllStudentSaved();
		List<Student> ls3 = new ArrayList();
		List<Student> ls4 = new ArrayList();
		
		
		for(int i=0;i<ls.size();i++) {
			if(ls.get(i).getCin().equals(cin)) {
				ls3.add(ls.get(i));
				ls3.forEach(x->mail.send(x.getEmail(),text,subject));
			}
		}
		
		for(int i=0;i<ls1.size();i++) {
			if(ls1.get(i).getCin().equals(cin)) {
				ls4.add(ls1.get(i));
				ls4.forEach(x->mail.send(x.getEmail(),text,subject1));
			}
		}
		
		
		
		
		//ls1.forEach(x->mail.send(x.getEmail(),text,subject1));
		
	}

	@Override
	public List<Student> getAllStudentCIN() {
		return em.createQuery("SELECT cin from Student s  ").getResultList();
		
	}

	@Override
	public void login(String cin) {
		
		List<Student> ls =em.createQuery("SELECT s from Student s  ").getResultList();
		List<Student> lss = new ArrayList();
		
		for (int i=0;i<ls.size();i++) {
			if(ls.get(i).getCin().equals(cin)) {
				System.out.println("ok");
				ls.get(i).setIsSaved(true);
				
				lss.add(ls.get(i));
			}
		}
		
	}


	
	



	/*@Override
	public void enregistrer(long cin) {
		
		//Student s = em.find(Student.class, cin);
		//List<Student> ls = new ArrayList();
		StudentEJB se = new StudentEJB();
		List<Student> ls  = se.em.createQuery("SELECT cin from Student s  ").getResultList();
		
		
		
		for(int i=0;i<ls.size();i++) {
			if(se.getAllStudentCIN().get(i).getCin()==cin) {
				
					System.out.println("this student  created"+ls.get(i));
					se.getAllStudentCIN().get(i).setIsSaved(true);
					em.persist(se.getAllStudentCIN().get(i));
					em.flush();
		
			}
		}
	
		
	}*/
	
	
}
