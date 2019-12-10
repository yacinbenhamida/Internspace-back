package com.internspace.ejb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPFileArchiveEJBLocal;
import com.internspace.ejb.abstraction.FYPFileModificationEJBLocal;
import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.exchanges.Mail_API;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.Notification;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.fyp.FYPFileArchive;
import com.internspace.entities.fyp.FYPFileModification;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.fyp.FYPIntervention.TeacherRole;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.FileTemplate;
import com.internspace.entities.university.CompanyCoordinates;
import com.internspace.entities.university.Departement;
import com.internspace.entities.university.StudyClass;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.users.Company;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;
import com.internspace.entities.users.User;

@Stateless
public class InternshipDirectorEJB implements InternshipDirectorEJBLocal{
	
	@PersistenceContext
	EntityManager em;
	Process mProcess;
	@Inject
	FYPFileArchiveEJBLocal serviceArchive;
	@Inject
	FYPFileModificationEJBLocal servicemodif;
	@Inject
	StudentEJBLocal studentserv;

	@Override
	public List<Student> getLateStudentsList(int year, long id) {
		String sql;
		//return em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.classYear =:year AND s.isCreated =false").setParameter("year", year).getResultList();
		List<StudyClass> ls = em.createQuery("FROM StudyClass").getResultList();
		if(year==0) {
			sql = "SELECT S FROM " + Student.class.getName() + " S JOIN FETCH S.studyClass SC JOIN FETCH SC.universitaryYear Y"
					+ " WHERE S.isCreated = :mybool AND SC.classOption.departement.site.university.id =:id";
			return em.createQuery(sql, Student.class)
					.setParameter("mybool", false).setParameter("id", id).getResultList();
		}
		else {
			sql = "SELECT S FROM " + Student.class.getName() + " S JOIN FETCH S.studyClass SC JOIN FETCH SC.universitaryYear Y"
				+ " WHERE Y.startDate = :year"
				+ " AND S.isCreated = :mybool AND SC.classOption.departement.site.university.id =:id" ;
		
		return em.createQuery(sql, Student.class)
		.setParameter("year", year).setParameter("mybool", false).setParameter("id", id).getResultList();
	}}

	
	@Override
	public void sendMail(int year, String text, long id) {
		String subject = "rappel pour saisir votre fiche PFE " ;
		Mailer mail = new Mailer();
		List<Student> ls = getLateStudentsList(year,id);
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
			lf.addAll(em.createQuery("SELECT fypFile FROM " + FYPSubject.class.getName()  + " c WHERE c.company =:company").setParameter("company", it.next()).getResultList());
	      }
		 //li.forEach(x->lf.add(x.getFypFile())); // Na7it l get khatr yrajaali f fypFile eli aando getSubject eli yrajaa f fypFile :( | achraf
		if(!lf.isEmpty()) 
		return lf;
		return null;
		
	
	}
	

	@Override
	public List<FYPFile> getFYPFileListByCategory(String category) {
		List<FYPFile> ls = new ArrayList<FYPFile>();
		FYPCategory cc  =(FYPCategory)em.createQuery("FROM FYPCategory f WHERE f.name =:name").setParameter("name",category).getSingleResult();
		 
		if(cc == null)
			return null;
		
		System.out.println("category: " + cc.getName());
		if(cc!= null)
			 ls = em.createQuery("FROM FYPFile f  JOIN FETCH f.categories CS WHERE CS.name = :name").setParameter("name", category).getResultList();
		return ls;
	}

	

	@Override
	public List<FYPFile> getFYPFileListSpecifique(int year , String location, FYPFileStatus state,String category ) {
		List<FYPFile> lf = new ArrayList();
		List<FYPFile> lff = new ArrayList();
		List<FYPFile> lfff = new ArrayList();
		List<FYPFile> lByCat = new ArrayList();
		List<FYPFile> rs = new ArrayList();
		boolean tata = true ;
		
		if(year == 0) {
			if(category==null) {
				lf.addAll(getFYPFileListByCountry(location));
				for (int i = 0; i < lf.size (); i++) {
					if(lf.get(i).getFileStatus()==state)
						rs.add(lf.get(i));
				}
			}
			else if(state==null) {
				lf.addAll(getFYPFileListByCountry(location));
				lByCat = getFYPFileListByCategory(category);
				for ( int i =0 ; i< lf.size(); i++) {
					for (int j =0 ; j< lByCat.size(); j++) {
						if(lf.get(i)==lByCat.get(j))
							rs.add(lf.get(i));
					}
				}
			}
			else if (location == null) {
				lByCat = getFYPFileListByCategory(category);
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lByCat.get(j).getFileStatus() == state)
						rs.add(lByCat.get(j));
				}
				
			}
			else {
			lf.addAll(getFYPFileListByCountry(location));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileStatus()==state)
					lfff.add(lf.get(i));
			}
			lByCat = getFYPFileListByCategory(category);
			for ( int i =0 ; i< lfff.size(); i++) {
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lfff.get(i)==lByCat.get(j))
						rs.add(lfff.get(i));
				}
			}
			}
			
		}
		/*******************************************************/
		else if(location==null){
				if(category == null) {
					lf.addAll(getFYPFileListByYear(year));
					for (int i = 0; i < lf.size (); i++) {
						if(lf.get(i).getFileStatus()==state)
							rs.add(lf.get(i));
					}
				}
				else if (state==null) {
					lf.addAll(getFYPFileListByYear(year));
					lByCat = getFYPFileListByCategory(category);
					for (int j =0 ; j< lf.size(); j++) {
						for(int i = 0 ; i< lByCat.size();i++ )
						if(lf.get(j)== lByCat.get(i))
							rs.add(lf.get(i));
					}
				}
				
				else {
			lf.addAll(getFYPFileListByYear(year));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileStatus()==state)
					lfff.add(lf.get(i));
			}
			lByCat = getFYPFileListByCategory(category);
			for ( int i =0 ; i< lfff.size(); i++) {
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lfff.get(i)==lByCat.get(j))
						rs.add(lfff.get(i));
				}
			}
		}
		}
		/*******************************************************/
		else if(state==null){
			if(category==null) {
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
			else {
			lf.addAll(getFYPFileListByCountry(location));
			lff = getFYPFileListByYear(year);
			for(int i=0 ; i<lff.size(); i++)
			{
				for(int j=0 ; j<lf.size(); j++) {
					if(lff.get(i).equals(lf.get(j)))
						lfff.add(lff.get(i));
				}
			}
			lByCat = getFYPFileListByCategory(category);
			for ( int i =0 ; i< lfff.size(); i++) {
				for (int j =0 ; j< lByCat.size(); j++) {
					if(lfff.get(i)==lByCat.get(j))
						rs.add(lfff.get(i));
				}
			}
			
		}
		}
		/*******************************************************/
		else if(category==null){
			lf.addAll(getFYPFileListByCountry(location));
			lff = getFYPFileListByYear(year);
			for(int i=0 ; i<lff.size(); i++)
			{
				for(int j=0 ; j<lf.size(); j++) {
					if(lff.get(i).equals(lf.get(j)))
						lfff.add(lff.get(i));
				}
			}
			for ( int i =0 ; i< lfff.size(); i++) {
				if(lfff.get(i).getFileStatus()==state)
					rs.add(lfff.get(i));
			}
		}
		
			/*lf.addAll(getFYPFileListByCountry(location));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileStatus()==state)
					rs.add(lf.get(i));
			}*/

		/*else if((location==null) & (category == null)){
			lf.addAll(getFYPFileListByYear(year));
			for (int i = 0; i < lf.size (); i++) {
				if(lf.get(i).getFileStatus()==state)
					rs.add(lf.get(i));
			}
		}
		else if((state==null) & (category == null)){
			lf.addAll(getFYPFileListByCountry(location));
			lff = getFYPFileListByYear(year);
			for(int i=0 ; i<lff.size(); i++)
			{
				for(int j=0 ; j<lf.size(); j++) {
					if(lff.get(i).equals(lf.get(j)))
						rs.add(lff.get(i));
				}
			}
			
		}*/
			
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
		Student i = (Student) em.createQuery("FROM Student s WHERE s.fypFile = :file").setParameter("file", f).getSingleResult();
		Notification n = new Notification();
		n.setSender(i);
		n.setContent("Refus de votre fiche PFE , verifier votre email pour plus d'information");
		em.persist(f);
		//em.persist(n);
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
		Student i = (Student) em.createQuery("FROM Student s WHERE s.fypFile = :file").setParameter("file", f).getSingleResult();
		mail.send(i.getEmail(),text,subject);
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
	
	public List<Student> getAllStudentsList(long id) {
		return em.createQuery("FROM " + Student.class.getName()  + " s WHERE s.studyClass.classOption.departement.site.university.id =:id").setParameter("id",id).getResultList();
		
	}
	
	public Student FindStudent(String cin, long id) {
		return (Student) em.createQuery("FROM Student s WHERE s.cin =:cin AND s.studyClass.classOption.departement.site.university.id =:id ").setParameter("cin", cin).setParameter("id",id).getSingleResult();	
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
    
		List<FYPFile> fpl = new ArrayList<FYPFile>();
		
		return em.createQuery("SELECT f from FYPFile f where (f.finalMark = 0) group by f.id"
				+ " HAVING (SELECT COUNT(i.id) FROM fyp_intervention"
				+ "  i where i.fypFile.id = f.id AND i.givenMark > 0)=2").getResultList();
		 
		/*List<Long> values= em.createQuery("SELECT fypFile.id FROM fyp_intervention i WHERE i.givenMark > 0 AND i.fypFile.finalMark = 0").getResultList();
		for (Long integer : values) {
			fpl.add(em.find(FYPFile.class,integer));
		}
		return fpl;*/
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
	public List<Departement> getDepartements(long id) {
		return em.createQuery("from Departement d WHERE d.site.university.id =:id").setParameter("id",id).getResultList();
	
	}
	@Override
	public Departement getDepartementInfo(long id) {
		return em.find(Departement.class, id);
	
	}
	@Override
	public void FixActionNumberAsSupervisor(int nb,long id) {
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForSupervisors(nb);
		
	}


	@Override
	public void FixActionNumberAsProtractor(int nb, long id) {
		// TODO Auto-generated method stub
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForProtractors(nb);
	}


	@Override
	public void FixActionNumberAsPreValidator(int nb, long id) {
		// TODO Auto-generated method stub
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForPreValidators(nb);
	}


	@Override
	public void FixActionNumberAsJuryPresident(int nb, long id) {
		// TODO Auto-generated method stub
		Departement d =em.find(Departement.class, id);
		d.setNumberOfActionsAllowedForPresidents(nb);
	}

	
	//pour la localisation sur la map
	@Override
	public List<FYPSubject> FullStudentInfoWithVerifiedCompanys(long id) {
		return em.createQuery("SELECT f.fypFile, f.company, f.fypFile.student FROM FYPSubject f WHERE f.fypFile.fileStatus ='confirmed' "
				+ "AND f.fypFile.student.studyClass.classOption.departement.site.university.id =:id").setParameter("id", id) .getResultList();
	}
	

	/**********************************************************************************************************************************
	 *	//pour verifier l'existance de la company return links from google															  *
	 **********************************************************************************************************************************/
	
	@Override
	public Boolean GetNameAndCountry(long id){
		Company c =em.find(Company.class,id);
		List<String> ls = new ArrayList<String>();
		Boolean verified = false;
		try{
			// /Library/Frameworks/Python.framework/Versions/3.8/bin/python3","/Users/Mahmoud/Documents/PI_BackEnd/Internspace-back/ch_Society.py","inwi","macroc"
			ProcessBuilder pb = new ProcessBuilder("/Library/Frameworks/Python.framework/Versions/3.8/bin/python3","/Users/Mahmoud/Documents/PI_BackEnd/Internspace-back/ch_Society.py",c.getName(),c.getCountry());
			Process p = pb.start(); 

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
	
			
			while ((s = in.readLine()) != null) {
				ls.add(s);
			}
			
			}catch(Exception e){System.out.println(e);}
		if(ls.size()>=1)
			verified=true;

				 return verified;
	}
	
	
	@Override
	public List<String> GetLinksOfCompany(long id){
		Company c =em.find(Company.class,id);
		List<String> ls = new ArrayList<String>();
		Boolean verified = false;
		try{
			// /Library/Frameworks/Python.framework/Versions/3.8/bin/python3","/Users/Mahmoud/Documents/PI_BackEnd/Internspace-back/ch_Society.py","inwi","macroc"
			ProcessBuilder pb = new ProcessBuilder("/Library/Frameworks/Python.framework/Versions/3.8/bin/python3","/Users/Mahmoud/Documents/PI_DEV/4eme - 1er semester/PI_BackEnd/Internspace-back/ch_Society.py",c.getName(),c.getCountry());
			Process p = pb.start(); 

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
	
			
			while ((s = in.readLine()) != null) {
				ls.add(s);
			}
			
			}catch(Exception e){System.out.println(e);}
	

				 return ls;
	}
	
	/**********************************************************************************************************************************
	 *	//Get panding FYPFile and change the status of comapny depends on the results of the Script Python ( use the before function )*
	 **********************************************************************************************************************************/
	 
	@Override
	public List<Object[]> getPendingFYPFile() {
		
		List<Object[]> list = em.createQuery("SELECT f.fypFile, f.company, f.fypFile.student FROM FYPSubject f WHERE f.fypFile.fileStatus ='pending'").getResultList();
		for (Object[] objects : list) {
			Company c = (Company)objects[1];
			if(c.getIsReal()==false) {
				c.setIsReal(this.GetNameAndCountry(c.getId()));
				em.persist(c);
			}
		}
		em.flush();
		
		return list;
	}
	
	 
		@Override
		public List<Object[]> getPendingFYPFileWithLinks() {
			
			List<Object[]> list = em.createQuery("SELECT f.fypFile, f.company, f.fypFile.student FROM FYPSubject f WHERE f.fypFile.fileStatus ='pending'").getResultList();
			
			
			return list;
		}
		
		@Override
		public CompanyCoordinates getCompanyCoordinates(long id) {
			return (CompanyCoordinates) em.createQuery("FROM CompanyCoordinates f WHERE f.company.id =:id").setParameter("id", id).getSingleResult();
			 
			
		}
	
	

	
	/*******************************
	* Not the work of Mahmoud !!!! *
	********************************/
	

	@Override
	public Student acceptPFE(long id) {
		Student s= em.find(Student.class, id);
		
		if(s == null)
			return null;
		
		if (s.getIsSaved()==true) {
		s.setIsAutorised(true);
		em.persist(s);
		em.flush();
		
		//password auto generaed
		Random rand = new Random();
		   String Xsi ="abcdefghijklmnopqrstuvwxyz";
		   final String Gamm ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";  
		   final String Iot = "1234567890";
		   final String Zeta="&~#|`-_)('/?,;:.";
		   String demo =""; 
		   double x =0;
		   
		   for (int k=0;k<100;k++){
				demo="";
			//randomisation des caractères selon leur nombre par type définis ,entre six et dix caratères
			        while ((demo.length() != 6)&& (demo.length() != 7)&& (demo.length() != 8)&& (demo.length() != 9)&& (demo.length() != 10)) {
			//selection aleatoire du type de caractère puis selection parmis les differents modèles de caractères              
			              int rPick=rand.nextInt(4);
			           if (rPick ==0) {
				      int erzat=rand.nextInt(25);
			              demo+=Xsi.charAt(erzat);
			         } else if (rPick == 1) {
				      int erzat=rand.nextInt(9);
				      demo+=Iot.charAt(erzat);
			         } else if (rPick==2) {
			              int erzat=rand.nextInt(25);
			              demo+=Gamm.charAt(erzat);
			         }else if (rPick==3) {
			              int erzat=rand.nextInt(15);
			              demo+=Zeta.charAt(erzat);
				}
				}
		   }
		
		//Mail
		   
		   Mail_API mail = new Mail_API();
			List<Student> ls = studentserv.getAllStudentAutorised();
			List<Student> ls1 = studentserv.getAllStudentNodisabled();
			List<Student> ls2 = studentserv.getAllStudentSaved();
			List<Student> ls3 = new ArrayList();
			List<Student> ls4 = new ArrayList();
			
			
			
				String subject1 = "Vous n'êtes pas autorisé a paser le PFE " ;
				String text="Bonjour Email Administration";
			    
			
			for(int i=0;i<ls.size();i++) {
				if(ls.get(i).getCin().equals(s.getCin())) {
					ls3.add(ls.get(i));
					  String subject = "vous êtes autorisé a passer votre PFE "
					    		+ "voici votre mot de passe " + demo ;
					  
					  ls.get(i).setPassGenerated(demo);
					//ls3.forEach(x->mail.send(x.getEmail(),text,subject));
					try {
						mail.sendMail(ls.get(i).getEmail(), text, subject);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}}
			
			if(ls1 == null)
				return null;
			
			for(int j=0;j<ls1.size();j++) {
				if(ls1.get(j).getCin().equals(s.getCin())) {
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
		
		
		
		
		
		
		
		
		
		
		return s;
		}
		return null;
		
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


	@Override
	public boolean acceptModification(long id) {
		/*FYPFileStatus f = null;
		FYPFile s= em.find(FYPFile.class, id);
		if(s.getFileStatus().equals(f.pending))
		{
			s.setIsConfirmed(true);
		}
		
		em.persist(s);
		em.flush();*/
		
		FYPFileModification f = em.find(FYPFileModification.class, id);
		
		if(f == null || f.getIsConfirmed() == true)
		{
			return false;
		}
		if(f.getIsChanged()== true) {
			f.setIsConfirmed(true);
			return true;
		}
		return false;
	}


	@Override
	public FYPFile acceptPFEFyp(long id) {
		FYPFile s= em.find(FYPFile.class, id);
		
		if(s == null)
			return null;
		
		
		s.setFileStatus(FYPFileStatus.confirmed);
		em.persist(s);
		em.flush();
		return s;
	



		
	}
	
	
	public FYPFile cancelPFEFyp(long id) {
		FYPFile s= em.find(FYPFile.class, id);
		
		if(s == null)
			return null;
		
		
		s.setFileStatus(FYPFileStatus.declined);
		em.persist(s);
		em.flush();
		return s;
	



		
	}
}




	


