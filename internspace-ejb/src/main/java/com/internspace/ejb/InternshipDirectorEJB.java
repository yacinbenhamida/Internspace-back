package com.internspace.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPFileArchive;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.Internship;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Student;

@Stateless
public class InternshipDirectorEJB implements InternshipDirectorEJBLocal{
	
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Student> getLateStudentsList(int year) {
		return em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.classYear =:year AND s.isCreated =false").setParameter("year", year).getResultList();
	}

	@Override
	public void sendMail(int year, String text) {
		String subject = "rappel pour saisir votre fiche PFE " ;
		Mailer mail = new Mailer();
		List<String> ls = em.createQuery("SELECT email FROM " + Student.class.getName()  + " s WHERE s.classYear =:year AND s.isCreated =false").setParameter("year", year).getResultList();
		ls.forEach(x->mail.send(x,text,subject));
	}

	@Override
	public List<FYPFile> getAllFYPFileList() {
		return em.createQuery("FROM " + FYPFile.class.getName()).getResultList();
	
	}

	@Override
	public List<FYPFile> getFYPFileListByState(FYPFileStatus state) {
		return em.createQuery("select * FROM FYPFile f WHERE f.fileTemplateElementType =:state").setParameter("state", state).getResultList();
	}

	@Override
	public List<FYPFile> getFYPFileListByYear( int year) {
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f WHERE f.year =:year").setParameter("year", year).getResultList();
	}

	//SELECT * FROM Employee e, Team t WHERE e.Id_team=t.Id_team
	@Override
	public List<FYPFile> getFYPFileListByCountry(String location) {
		List<Internship> li = new ArrayList();
		List<FYPFile> lf = new ArrayList();
		List<Company> lc = em.createQuery("FROM " + Company.class.getName()  + " c WHERE c.country =:location").setParameter("location", location).getResultList();
		ListIterator<Company> it = lc.listIterator();
		while(it.hasNext()){  
			li.addAll(em.createQuery("FROM " + Internship.class.getName()  + " c WHERE c.company =:company").setParameter("company", it.next()).getResultList());
	      }
		 li.forEach(x->lf.add(x.getFypFile()));
		 return lf;
		
	}

	@Override
	public List<FYPFile> getFYPFileListSpecifique(int year , String location, FYPFileStatus state) {
		List<FYPFile> lf = new ArrayList();
		List<FYPFile> rs = new ArrayList();
		if(year == 0) {
			lf.addAll(getFYPFileListByCountry(location));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileTemplateElementType()==state)
					rs.add(lf.get(i));
			}
		}
		else if(location==""){
			lf.addAll(getFYPFileListByYear(year));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileTemplateElementType()==state)
					rs.add(lf.get(i));
			}
		}
		else{
			lf.addAll(getFYPFileListByCountry(location));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getyear()==year)
					rs.add(lf.get(i));
			}
		}
			
		return rs;
	}

	@Override
	public List<FYPFile> getFYPFileListCurrentYear(FYPFileStatus state) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f WHERE f.year =:year AND f.fileTemplateElementType =:state").setParameter("year", year).getResultList();
	}

	@Override
	public void acceptFile(int id ) {
		FYPFile f = em.find(FYPFile.class, id);
		f.setFileTemplateElementType(FYPFileStatus.confirmed);
		em.persist(f);
		em.flush();
		
	}

	@Override
	public void refuseFile(int id , String text) {
		String subject = "Refus d'une fiche PFE" ;
		FYPFile f = em.find(FYPFile.class, id);
		f.setFileTemplateElementType(FYPFileStatus.declined);
		Internship i = f.getInternship();
		Notification n = new Notification();
		n.setStudent(i.getStudent());
		n.setContent("Refus de votre fiche PFE , verifier votre email pour plus d'information");
		em.persist(f);
		em.persist(n);
		em.flush();
		Mailer mail = new Mailer();
		List<Student> ls =em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.internship =:intern").setParameter("intern", i).getResultList();
		mail.send(ls.get(1).getEmail(),text,subject);
	}

	@Override
	public void acceptCancelingDemand(int id) {
		FYPFile f = em.find(FYPFile.class, id);
		FYPFileArchive Farchive = new FYPFileArchive() ;
		Farchive.setArchiveDate(new Date());
		Farchive.setCategories(f.getCategories());
		Farchive.setDescription(f.getDescription());
		Farchive.setFeatures(f.getFeatures());
		Farchive.setFileTemplateElementType(f.getFileTemplateElementType());
		Farchive.setId(f.getId());
		Farchive.setInternship(f.getInternship());
		Farchive.setInterventions(f.getInterventions());
		Farchive.setKeywords(f.getKeywords());
		Farchive.setProblematic(f.getProblematic());
		Farchive.setTitle(f.getTitle());
		Farchive.setUniversitaryYear(f.getUniversitaryYear());
		em.persist(Farchive);
		em.flush();
		
	}

	@Override
	public void declineCancelingDemand(int id , String text) {
		FYPFile f = em.find(FYPFile.class, id);
		String subject = "refus l’annulation d’un stage PFE" ;
		f.setIsCanceled(false);
		Mailer mail = new Mailer();
		Internship i = f.getInternship();
		List<Student> ls =em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.internship =:intern").setParameter("intern", i).getResultList();
		mail.send(ls.get(1).getEmail(),text,subject);
	}

	@Override
	public List<FYPFile> listCancelingDemand() {
		Boolean state = true;
		return em.createQuery("select * FROM FYPFile f WHERE f.isCanceled =:state").setParameter("state", state).getResultList();
		
	}
	
	
	
	

}
