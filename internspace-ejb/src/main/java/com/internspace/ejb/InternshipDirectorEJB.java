package com.internspace.ejb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.fyp.FYPFile;
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
	public void sendMail(int year) {
		Mailer mail = new Mailer();
		List<String> ls = em.createQuery("SELECT email FROM " + Student.class.getName()  + " s WHERE s.classYear =:year AND s.isCreated =false").setParameter("year", year).getResultList();
		ls.forEach(x->mail.send(x));
	}

	@Override
	public List<FYPFile> getAllFYPFileList() {
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f").getResultList();
	
	}

	@Override
	public List<FYPFile> getFYPFileListByState(String state) {
		return em.createQuery("FROM " + FYPFile.class.getName()  + " f WHERE f.state =:state").setParameter("state", state).getResultList();

	}

	@Override
	public List<FYPFile> getFYPFileListByYear(int year) {
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
	public List<FYPFile> getFYPFileListSpecifique() {
		// TODO Auto-generated method stub
		return null;
	}

}
