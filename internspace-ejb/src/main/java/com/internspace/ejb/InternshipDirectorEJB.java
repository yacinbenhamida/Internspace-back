package com.internspace.ejb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPFileArchive;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.fyp.FYPIntervention.TeacherRole;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.fyp.Internship;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Student;

@Stateless
public class InternshipDirectorEJB implements InternshipDirectorEJBLocal{
	
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Student> getLateStudentsList(int year) {
		//return em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.classYear =:year AND s.isCreated =false").setParameter("year", year).getResultList();
		List<StudyClass> ls = em.createQuery("FROM StudyClass").getResultList();
		List<StudyClass> FiltredLs = new ArrayList<StudyClass>();
		List<Student> l = new ArrayList<Student>();
		List<Student> rs = new ArrayList<Student>();
		 for(int i=0;i<ls.size();i++) {
			 if(ls.get(i).getUniversitaryYear().getStartDate()==year)
				 FiltredLs.add(ls.get(i));
		 }
		 FiltredLs.forEach(x->l.addAll(x.getStudents()));
		 for(int i=0;i<l.size();i++) {
			if(l.get(i).getIsCreated()==false)
				 rs.add(l.get(i))	 ;
		 }
		 return rs;
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
		Internship i = (Internship)em.createQuery("FROM " + Internship.class.getName()  + " i WHERE i.fypFile =:file").setParameter("file", f).getSingleResult();
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
	public void acceptCancelingDemand(long id) {
		FYPFile f = em.find(FYPFile.class, id);
		FYPFileArchive Farchive = new FYPFileArchive() ;
		f.setIsArchived(true);
		Farchive.setFypFile(f);
		Farchive.setArchiveDate(new Date());
		em.persist(f);
		em.persist(Farchive);
		em.flush();
		
	}

	@Override
	public void declineCancelingDemand(long id , String text) {
		FYPFile f = em.find(FYPFile.class, id);
		String subject = "refus l’annulation d’un stage PFE" ;
		f.setIsCanceled(false);
		Mailer mail = new Mailer();
		Internship i = (Internship)em.createQuery("FROM " + Internship.class.getName()  + " i WHERE i.fypFile =:file").setParameter("file", f).getSingleResult();
		mail.send(i.getStudent().getEmail(),text,subject);
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
		 return em.createQuery("SELECT f from FYPFile f where f.finalMark = 0 group by f.id"
				+ " HAVING (SELECT COUNT(i.id) FROM FYP_INTERVENTION i where i.internshipSheet.id = f.id AND i.givenMark != NULL )=2").getResultList();
	}


	@Override
	public FYPFile FilterWaitingForDefensePlanningList(long id) {
		List<FYPFile> xx = WaitingForDefensePlanningList();
		List<Long> ls = em.createQuery("SELECT s.internship.fypFile.id FROM " + Student.class.getName() + " s"
				+ " WHERE s.id =:id ").setParameter("id", id).getResultList();
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


	/*List<FYPFile> ls = getAllFYPFileList();
		List<FYPFile> rs = new ArrayList<FYPFile>();
		TeacherRole protractor = TeacherRole.protractor;
		TeacherRole  supervisor = TeacherRole.supervisor;
		int noteP=0;
		int noteSup=0;*/
	
	/*for(int i=0; i<ls.size();i++) {
	List<FYPIntervention> interLS= ls.get(i).getInterventions();
	for(int j=0; j<interLS.size();j++) {
		
		if(interLS.get(j).getTeacherRole()== protractor)
		{
			noteP = interLS.get(j).getGivenMark();
		}
		if(interLS.get(j).getTeacherRole()== supervisor)
		{
			noteSup = interLS.get(j).getGivenMark();
		}
		
	}
	if(noteSup > 0 && noteP > 0)
		rs.add(ls.get(i));
		
}*/
	
	
	
	
	

}
