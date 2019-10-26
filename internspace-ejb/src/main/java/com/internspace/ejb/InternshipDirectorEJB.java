package com.internspace.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPFileArchiveEJBLocal;
import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPFileArchive;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.fyp.FYPIntervention.TeacherRole;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.FileTemplate;

import com.internspace.entities.university.Departement;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;

@Stateless
public class InternshipDirectorEJB implements InternshipDirectorEJBLocal{
	
	@PersistenceContext
	EntityManager em;
	
	@Inject
	FYPFileArchiveEJBLocal serviceArchive;

	@Override
	public List<Student> getLateStudentsList(int year) {
		//return em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.classYear =:year AND s.isCreated =false").setParameter("year", year).getResultList();
		List<StudyClass> ls = em.createQuery("FROM StudyClass").getResultList();
		String sql = "SELECT S FROM " + Student.class.getName() + " S JOIN FETCH S.studyClass SC JOIN FETCH SC.universitaryYear Y"
				+ " WHERE Y.startDate = :year"
				+ " AND S.isCreated = :mybool";
		return em.createQuery(sql, Student.class)
		.setParameter("year", year).setParameter("mybool", false).getResultList();
	}

	
	@Override
	public void sendMail(int year, String text) {
		String subject = "rappel pour saisir votre fiche PFE " ;
		Mailer mail = new Mailer();
		List<Student> ls = getLateStudentsList(year);
		ls.forEach(x->mail.send(x.getEmail(),text,subject));
	}

	@Override
	public List<FYPFile> getAllFYPFileList() {
		return em.createQuery("FROM FYPFile f").getResultList();
	
	}

	@Override
	public List<FYPFile> getFYPFileListByState(FYPFileStatus state) {
		return em.createQuery("FROM FYPFile f WHERE f.fileStatus =:state").setParameter("state", state).getResultList();
	}

	@Override
	public List<FYPFile> getFYPFileListByYear( int year) {
		UniversitaryYear uy = (UniversitaryYear)em.createQuery("FROM UniversitaryYear y WHERE y.startDate=:year").setParameter("year", year).getSingleResult();
		 List<FYPFile> ls = em.createQuery("FROM FYPFile f WHERE f.universitaryYear=:uy").setParameter("uy", uy).getResultList();
		 return ls;
	}

	//SELECT * FROM Employee e, Team t WHERE e.Id_team=t.Id_team
	
	@Override
	public List<FYPFile> getFYPFileListByCountry(String location) {
		
		List<FYPSubject> li = new ArrayList();
		List<FYPFile> lf = new ArrayList();
		List<Company> lc = em.createQuery("FROM " + Company.class.getName()  + " c WHERE c.country =:location").setParameter("location", location).getResultList();
		ListIterator<Company> it = lc.listIterator();
		while(it.hasNext()){  
			li.addAll(em.createQuery("FROM " + FYPSubject.class.getName()  + " c WHERE c.company =:company").setParameter("company", it.next()).getResultList());
	      }
		 //li.forEach(x->lf.add(x.getFypFile())); // Na7it l get khatr yrajaali f fypFile eli aando getSubject eli yrajaa f fypFile :( | achraf
		 return lf;
		
	
	}

	@Override
	public List<FYPFile> getFYPFileListSpecifique(int year , String location, FYPFileStatus state) {
		List<FYPFile> lf = new ArrayList();
		List<FYPFile> lff = new ArrayList();
		List<FYPFile> rs = new ArrayList();
		if(year == 0) {
			lf.addAll(getFYPFileListByCountry(location));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileStatus()==state)
					rs.add(lf.get(i));
			}
		}
		else if(location==null){
			lf.addAll(getFYPFileListByYear(year));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileStatus()==state)
					rs.add(lf.get(i));
			}
		}
		else{
			lf.addAll(getFYPFileListByCountry(location));
			lff = getFYPFileListByYear(year);
			for(int i=0 ; i<lff.size(); i++)
			{
				for(int j=0 ; j<lf.size(); j++) {
					if(lff.get(i).equals(lf.get(j)))
						rs.add(lff.get(i));
				}
			}
			
		}
			
		return rs;
	}

	@Override
	public List<FYPFile> getFYPFileListCurrentYear(FYPFileStatus state) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<FYPFile> rs ;
		UniversitaryYear uy = (UniversitaryYear)em.createQuery("FROM UniversitaryYear y WHERE y.startDate=:year").setParameter("year", year).getSingleResult();
		 List<FYPFile> ls = em.createQuery("FROM FYPFile f WHERE f.universitaryYear=:uy AND f.fileStatus=:state").setParameter("uy", uy).setParameter("state", state).getResultList();
		 return ls;
	}

	@Override
	public void acceptFile(long id ) {
		FYPFile f = em.find(FYPFile.class, id);
		f.setFileStatus(FYPFileStatus.confirmed);
		em.persist(f);
		em.flush();
		
	}

	@Override
	public void refuseFile(long id , String text) {
		String subject = "Refus d'une fiche PFE" ;
		FYPFile f = em.find(FYPFile.class, id);
		f.setFileStatus(FYPFileStatus.declined);
		Student i = f.getStudent();
		Notification n = new Notification();
		n.setStudent(i);
		n.setContent("Refus de votre fiche PFE , verifier votre email pour plus d'information");
		em.persist(f);
		em.persist(n);
		em.flush(); 
		Mailer mail = new Mailer();
		//List<Student> ls =em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.internship =:intern").setParameter("intern", i).getResultList();
		mail.send(i.getEmail(),text,subject);
	}

	@Override
	public void acceptCancelingDemand(long id) {
		FYPFile f = em.find(FYPFile.class, id);
		f.setIsArchived(true);
		em.persist(f);
		serviceArchive.addFileToArchive(f);
		em.flush();
		
	}

	@Override
	public void declineCancelingDemand(long id , String text) {
		
		FYPFile f = em.find(FYPFile.class, id);
		String subject = "refus l’annulation d’un stage PFE" ;
		f.setIsCanceled(false);
		Mailer mail = new Mailer();
		mail.send(f.getStudent().getEmail(),text,subject);
		em.persist(f);
		em.flush();
		
	}

	@Override
	public List<FYPFile> listCancelingDemand() {
		Boolean state = true;
		Boolean archive = false;
		return em.createQuery("FROM FYPFile f WHERE f.isCanceled =:state AND f.isArchived =:archive")
				.setParameter("state", state)
				.setParameter("archive", archive).getResultList();
		
	}

	@Override
	public boolean disableAccount(long id) {
		Student s =em.find(Student.class, id);
		if(s!=null)
		{
			s.setIsDisabled(true);
			em.persist(s);
			em.flush();
			return true;
		}
		return false;
	}
	
	public List<Student> getAllStudentsList() {
		return em.createQuery("FROM " + Student.class.getName()  + " s").getResultList();
		
	}
	
	public Student FindStudent(long id) {
		return em.find(Student.class, id);	
	}

	@Override
	public Boolean ValidateSubmittedAReport(long id) {
		Student s = em.find(Student.class, id);
		s.setHasSubmittedAreport(true);
		em.persist(s);
		em.flush();
		return true;
	}


	@Override
	public List<FYPFile> WaitingForDefensePlanningList() {
		
		// shouf m3a yassin el faza !!!! MAHMOUD
		 return em.createQuery("SELECT f from FYPFile f where (f.finalMark = 0 OR f.finalMark = NULL) group by f.id"
				+ " HAVING (SELECT COUNT(i.id) FROM fyp_intervention"
				+ "  i where i.fypFile.id = f.id AND (i.givenMark != NULL OR i.givenMark = 0) )=2").getResultList();
	}


	@Override
	public FYPFile FilterWaitingForDefensePlanningList(String cin, String nom) {
		List<FYPFile> xx = WaitingForDefensePlanningList();
		List<Long> ls;
		
		if(nom==null)
		ls = em.createQuery("SELECT s.fypFile.id FROM " + Student.class.getName() + " s"
				+ " WHERE s.cin =:cin ").setParameter("cin", cin).getResultList();
		else
			ls = em.createQuery("SELECT s.fypFile.id FROM " + Student.class.getName() + " s"
					+ " WHERE s.firstName =:name ").setParameter("name", nom).getResultList();
		
		if(ls.isEmpty()) {
			return null;
		}
			else {
		for(int i =0; i<xx.size(); i++) {
			if(xx.get(i).getId()==ls.get(0))
				return xx.get(i);
		}
		
		}
		
		return null;
	}


	@Override
	public void FixActionNumberAsSupervisor(int nb,int id) {
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForSupervisors(nb);
		
	}


	@Override
	public void FixActionNumberAsProtractor(int nb, int id) {
		// TODO Auto-generated method stub
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForProtractors(nb);
	}


	@Override
	public void FixActionNumberAsPreValidator(int nb, int id) {
		// TODO Auto-generated method stub
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForPreValidators(nb);
	}


	@Override
	public void FixActionNumberAsJuryPresident(int nb, int id) {
		// TODO Auto-generated method stub
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForPresidents(nb);
	}



	@Override
	public void acceptPFE(long id) {
		Student s= em.find(Student.class, id);
		if (s.getIsSaved()==true) {
		s.setIsAutorised(true);
		em.persist(s);
		em.flush();}
		else
		{
			System.out.println("this student is not created");
		}
		
	}


	@Override
	public Employee getInternshipDirectorById(long id)
	{
		Employee employee = em.find(Employee.class, id);
		
		if(employee != null && !employee.getRole().equals(Employee.Role.internshipsDirector))
		{
			return null;
		}
		
		return employee;
	}
	
	
	
	
	

}
