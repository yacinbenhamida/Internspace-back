package com.internspace.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.exchanges.Mail_API;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.MailerStudent;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.users.Employee;
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
	public List<Student> getAllStudentAutorised() {
		return em.createQuery("SELECT s from Student s  where s.isAutorised=:isAutorised").setParameter("isAutorised", true).getResultList();
		
	}

	@Override
	public List<Student> getAllStudentNodisabled() {
		return em.createQuery("SELECT s from Student s  where s.isAutorised=:isAutorised").setParameter("isAutorised", false).getResultList();
		
	}
	
	
	@Override
	public void sendMail(String text,String cin) {
		
		String subject = "vous êtes autorisé a passer votre PFE " ;
		String subject1 = "Vous n'êtes pas autorisé a paser le PFE " ;
		
		
		Mail_API mail = new Mail_API();
		List<Student> ls = getAllStudentAutorised();
		List<Student> ls1 = getAllStudentNodisabled();
		List<Student> ls2 = getAllStudentSaved();
		List<Student> ls3 = new ArrayList();
		List<Student> ls4 = new ArrayList();
		
		
		for(int i=0;i<ls.size();i++) {
			if(ls.get(i).getCin().equals(cin)) {
				ls3.add(ls.get(i));
				//ls3.forEach(x->mail.send(x.getEmail(),text,subject));
				try {
					mail.sendMail(ls.get(i).getEmail(), text, subject);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}}
		
		
		for(int j=0;j<ls1.size();j++) {
			if(ls1.get(j).getCin().equals(cin)) {
				ls4.add(ls1.get(j));
				try {
					mail.sendMail(ls1.get(j).getEmail(), text, subject1);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ls4.forEach(x->mail.send(x.getEmail(),text,subject1));
			}
		}
		}
		
		
		
		
		//ls1.forEach(x->mail.send(x.getEmail(),text,subject1));
		
	

	@Override
	public List<FYPFile> getAllStudentFile() {
		return em.createQuery("SELECT fypFile from Student c  where c.isAutorised=:isAutorised").setParameter("isAutorised", true).getResultList();
		
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

	

	@Override
	public List<FYPFile> getAllStudentFileCin(String cin) {
		return em.createQuery("SELECT fypFile from Student c  where c.isAutorised=:isAutorised AND c.cin=:cin").setParameter("isAutorised", true).setParameter("cin", cin).getResultList();
		
	}

	@Override
	public void mailEtat(String text, String cin) {
		
		String subject = "Votre fiche PFE est acceptée " ;
		String subject1 = "Votre fiche PFE est refusée" ;
		
		FYPFileStatus ff = null;

		Mail_API mail = new Mail_API();
		
		//ff.confirmed.compareTo(fs);
		List<Student> ls = getAllStudentAutorised();
		List<FYPFile> ls1 = getAllStudentFile();
		List<FYPFile> lc =  getAllStudentFileCin(cin);
		List<Student> ls11 = getAllStudentCin(cin);
		
		for(int i=0;i<lc.size();i++) {
			FYPFileStatus fs = lc.get(i).getFileStatus();
			
		
				System.out.println("changed"+lc.get(i).getFileStatus());
			  if(lc.get(i).getFileStatus().equals(ff.pending)) {
				
				if(lc.get(i).getIsArchived()==true) {
					lc.get(i).setFileStatus(ff.confirmed);
					
					try {
						mail.sendMail(ls11.get(i).getEmail(), text, subject);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("changed"+lc.get(i).getIsArchived());
				}
				else if(lc.get(i).getIsCanceled()==true)
				{
					lc.get(i).setFileStatus(ff.declined);
					
					
					try {
						mail.sendMail(ls11.get(i).getEmail(), text, subject1);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("canceled");
				}
				
				
				
				
			
			  
			  
			  
			
			}
		}
		}

	@Override
	public List<Student> getAllStudentCin(String cin) {
		return em.createQuery("SELECT c from Student c  where c.isAutorised=:isAutorised AND c.cin=:cin").setParameter("isAutorised", true).setParameter("cin", cin).getResultList();
		
	}

	@Override
	public List<Employee> getDirector(String cin) {
		//List <String> list = new ArrayList();
		List<FYPFile> ls = getAllStudentFileCin(cin);
	
				for(int i=0;i<ls.size();i++) {
			if(ls.get(i).getIsArchived()) {
				
				/*************************************************************************************
				* getInterventions() ta3mél boucle infini f listing mte3 FYPFile .. make it with SQL *
				*************************************************************************************/
				List<FYPIntervention> lf  = em.createQuery("SELECT c.interventions from "+ FYPFile.class.getName()+" c  where c.student.isAutorised=:isAutorised AND c.student.cin=:cin ").setParameter("isAutorised", true).setParameter("cin", cin).getResultList();
				//List<FYPIntervention> lf = ls.get(i).getInterventions();
				for(int j=0;j<lf.size();j++) {
					/*String name = lf.get(j).getTeacher().getUsername();
					String email = lf.get(j).getTeacher().getEmail();
					
					list.add(name);
					list.add(email);*/
					return em.createQuery("SELECT c.teacher.firstName,c.teacher.email from "+ FYPIntervention.class.getName()+" c  ").getResultList();
					
					//return list;
					
				}
				
				//List<FYPIntervention> lf = em.createQuery("SELECT c.id from FYPIntervention c  where c.fypFile=:id").setParameter("id", ls.get(i).getId()).getResultList();
				
			}
		}
		//return em.createQuery("SELECT c.teacher.firstName,c.teacher.email from "+ FYPIntervention.class.getName()+" c  ").getResultList();
		return null;
	}

	@Override
	public List<FYPFile> getAllSheetsPendingStudent() {
		
		
		return em.createQuery("SELECT s.fypFile.title,s from "+Student.class.getName()+" s  where s.fypFile.fileStatus =:status").setParameter("status", FYPFileStatus.pending).getResultList();
	
		
	}

	@Override
	public List<FYPFile> getAllSheetsPendingByStudent(String cin) {
		//Student s = em.find(Student.class, id);
		return em.createQuery("SELECT  s.fypFile.title,s from "+Student.class.getName()+" s  where s.fypFile.fileStatus =:status AND s.cin =:cin").setParameter("cin", cin).setParameter("status", FYPFileStatus.pending).getResultList();
		
		
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
