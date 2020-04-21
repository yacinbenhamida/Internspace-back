package com.internspace.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPFileModificationEJBLocal;
import com.internspace.ejb.abstraction.FYPSheetHistoryEJBLocal;
import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.ejb.abstraction.StudyClassesEJBLocal;
import com.internspace.entities.exchanges.Mail_API;
import com.internspace.entities.exchanges.Mailer;
import com.internspace.entities.exchanges.MailerStudent;
import com.internspace.entities.fyp.FYPCategory;
import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.university.UniversitaryYear;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.fyp.FYPSheetHistory;
import com.internspace.entities.fyp.FYPSubject;
import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;

@Stateless
public class StudentEJB implements StudentEJBLocal {

	@PersistenceContext
	EntityManager em;

	@Inject
	InternshipDirectorEJBLocal serv;

	@Inject
	FYPSheetHistoryEJBLocal hist;
	@Inject
	FYPFileModificationEJBLocal modifFyle;
	@Inject
	StudyClassesEJBLocal classe;
	@Override
	public void addStudent(Student std) {
		System.out.println("Adding: " + std);
		std.setIsAutorised(false);
		std.setStudyClass(classe.getClassById(new Random().nextInt(10)));
		em.persist(std);
	}

	@Override
	public List<Student> getAllStudentLateYear() {

		return em.createQuery("SELECT s from Student s where s.studyClass.classYear=:year").setParameter("year", 5)
				.getResultList();
	}

	@Override
	public Student enregistrerAuPlatforme(String cin) {

		List<Student> ls = getAllStudentLateYear();

		if (ls == null)
			return null;

		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).getCin().equals(cin) && ls.get(i).getIsSaved() == false) {
				System.out.println("ok");
				ls.get(i).setIsSaved(true);

				return ls.get(i);

			}
		}

		return null;
	}

	@Override
	public List<Student> getAll() {
		return em.createQuery("SELECT c from Student c").getResultList();

	}

	@Override
	public List<Student> getAllStudentSaved() {

		return em.createQuery("SELECT s from Student s  where s.isSaved=:isSaved").setParameter("isSaved", true)
				.getResultList();
	}

	@Override
	public List<Student> getAllStudentAutorised() {
		return em.createQuery("SELECT s from Student s  where s.isAutorised=:isAutorised")
				.setParameter("isAutorised", true).getResultList();

	}

	@Override
	public List<Student> getAllStudentNodisabled() {
		return em.createQuery("SELECT s from Student s  where s.isAutorised=:isAutorised")
				.setParameter("isAutorised", false).getResultList();

	}

	@Override
	public void sendMail(String text, String cin) {

		/*
		 * Random rand = new Random(); String Xsi ="abcdefghijklmnopqrstuvwxyz"; final
		 * String Gamm ="ABCDEFGHIJKLMNOPQRSTUVWXYZ"; final String Iot = "1234567890";
		 * final String Zeta="&~#|`-_)('/?,;:."; String demo =""; double x =0;
		 * 
		 * for (int k=0;k<100;k++){ demo=""; //randomisation des caractères selon leur
		 * nombre par type définis ,entre six et dix caratères while ((demo.length() !=
		 * 6)&& (demo.length() != 7)&& (demo.length() != 8)&& (demo.length() != 9)&&
		 * (demo.length() != 10)) { //selection aleatoire du type de caractère puis
		 * selection parmis les differents modèles de caractères int
		 * rPick=rand.nextInt(4); if (rPick ==0) { int erzat=rand.nextInt(25);
		 * demo+=Xsi.charAt(erzat); } else if (rPick == 1) { int erzat=rand.nextInt(9);
		 * demo+=Iot.charAt(erzat); } else if (rPick==2) { int erzat=rand.nextInt(25);
		 * demo+=Gamm.charAt(erzat); }else if (rPick==3) { int erzat=rand.nextInt(15);
		 * demo+=Zeta.charAt(erzat); } } }
		 * 
		 */

		Mail_API mail = new Mail_API();
		List<Student> ls = getAllStudentAutorised();
		List<Student> ls1 = getAllStudentNodisabled();
		List<Student> ls2 = getAllStudentSaved();
		List<Student> ls3 = new ArrayList();
		List<Student> ls4 = new ArrayList();

		String subject1 = "Vous n'êtes pas autorisé a passer le PFE " + text;

		String text1 = " refus de PFE";
		for (int i = 0; i < ls.size(); i++) {

			if (ls1 == null)
				return;

			for (int j = 0; j < ls1.size(); j++) {
				if (ls1.get(j).getCin().equals(cin)) {
					ls4.add(ls1.get(j));
					try {
						mail.sendMail(ls1.get(j).getEmail(), text1, subject1);
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// ls4.forEach(x->mail.send(x.getEmail(),text,subject1));
				}
			}
		}
	}

	// ls1.forEach(x->mail.send(x.getEmail(),text,subject1));

	@Override
	public List<FYPFile> getAllStudentFile() {
		return em.createQuery("SELECT fypFile from Student c  where c.isAutorised=:isAutorised")
				.setParameter("isAutorised", true).getResultList();

	}

	@Override
	public void login(String cin) {

		List<Student> ls = em.createQuery("SELECT s from Student s  ").getResultList();
		List<Student> lss = new ArrayList();

		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).getCin().equals(cin)) {
				System.out.println("ok");
				ls.get(i).setIsSaved(true);

				lss.add(ls.get(i));
			}
		}

	}

	@Override
	public List<FYPFile> getAllStudentFileCin(String cin) {
		return em.createQuery("SELECT c.fypFile from Student c  where c.isAutorised=:isAutorised AND c.cin=:cin")
				.setParameter("isAutorised", true).setParameter("cin", cin).getResultList();

	}

	@Override
	public List<FYPFeature> getAllStudentFileCinFeatures(String cin) {
		return em
				.createQuery(
						"SELECT c.fypFile.features from Student c  where c.isAutorised=:isAutorised AND c.cin=:cin")
				.setParameter("isAutorised", true).setParameter("cin", cin).getResultList();

	}

	@Override
	public void mailEtat(String text, String cin) {

		String subject = "Votre fiche PFE est acceptée ";
		String subject1 = "Votre fiche PFE est refusée";

		FYPFileStatus ff = null;

		Mail_API mail = new Mail_API();

		// ff.confirmed.compareTo(fs);
		List<Student> ls = getAllStudentAutorised();
		List<FYPFile> ls1 = getAllStudentFile();
		List<FYPFile> lc = getAllStudentFileCin(cin);
		List<Student> ls11 = getAllStudentCin(cin);

		List<FYPFile> lh = hist.getAllFiles();

		for (int i = 0; i < lc.size(); i++) {
			FYPFileStatus fs = lc.get(i).getFileStatus();
			for (int k = 0; k < lh.size(); k++) {
				if (lc.get(i).equals(lh.get(k))) {

					if (lh.get(k).getFileStatus().equals(fs.confirmed)) {

						try {
							mail.sendMail(ls11.get(i).getEmail(), text, subject);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if (lh.get(k).getFileStatus().equals(fs.declined)) {

						try {
							mail.sendMail(ls11.get(i).getEmail(), text, subject1);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			}

			/*
			 * System.out.println("changed"+lc.get(i).getFileStatus());
			 * if(lc.get(i).getFileStatus().equals(ff.pending)) {
			 * 
			 * if(lc.get(i).getIsArchived()==true) { lc.get(i).setFileStatus(ff.confirmed);
			 * 
			 * try { mail.sendMail(ls11.get(i).getEmail(), text, subject); } catch
			 * (MessagingException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 * 
			 * System.out.println("changed"+lc.get(i).getIsArchived()); } else
			 * if(lc.get(i).getIsCanceled()==true) { lc.get(i).setFileStatus(ff.declined);
			 * 
			 * 
			 * try { mail.sendMail(ls11.get(i).getEmail(), text, subject1); } catch
			 * (MessagingException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 * 
			 * System.out.println("canceled"); }
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * }
			 */
		}
	}

	@Override
	public List<Student> getAllStudentCin(String cin) {
		return em.createQuery("SELECT c from Student c  where c.isAutorised=:isAutorised AND c.cin=:cin")
				.setParameter("isAutorised", true).setParameter("cin", cin).getResultList();

	}

	@Override
	public List<Employee> getDirector(long id) {
		// List <String> list = new ArrayList();

		
		Student std = em.find(Student.class, id);
		String cin = std.getCin();
		List<FYPFile> ls = getAllStudentFileCin(cin);
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).getIsArchived()) {

				/*************************************************************************************
				 * getInterventions() ta3mél boucle infini f listing mte3 FYPFile .. make it
				 * with SQL *
				 *************************************************************************************/
				List<FYPIntervention> lf = em
						.createQuery("SELECT c.interventions from " + FYPFile.class.getName()
								+ " c  where c.student.isAutorised=:isAutorised AND c.student.cin=:cin ")
						.setParameter("isAutorised", true).setParameter("cin", cin).getResultList();
				// List<FYPIntervention> lf = ls.get(i).getInterventions();
				for (int j = 0; j < lf.size(); j++) {
					/*
					 * String name = lf.get(j).getTeacher().getUsername(); String email =
					 * lf.get(j).getTeacher().getEmail();
					 * 
					 * list.add(name); list.add(email);
					 */
					return em.createQuery("SELECT  c.teacher.id,c.teacher.role,c.teacher.firstName,c.teacher.email from "
							+ FYPIntervention.class.getName() + " c where c.fypFile.student.cin=:cin ").setParameter("cin", cin).getResultList();

					// return list;

				}

				// List<FYPIntervention> lf = em.createQuery("SELECT c.id from FYPIntervention c
				// where c.fypFile=:id").setParameter("id", ls.get(i).getId()).getResultList();

			}
		}
		// return em.createQuery("SELECT c.teacher.firstName,c.teacher.email from "+
		// FYPIntervention.class.getName()+" c ").getResultList();
		return null;
	}

	@Override
	public List<FYPFile> getAllSheetsPendingStudent() {

		return em
				.createQuery("SELECT s.fypFile.title,s from " + Student.class.getName()	+ " s  where s.fypFile.fileStatus =:status")
				.setParameter("status", FYPFileStatus.pending).getResultList();

	}

	@Override
	public List<FYPFile> getAllSheetsPendingByStudent(String cin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student authentification(String cin, String password) {
		List<Student> ls = getAllStudentAutorised();
		List<String> name = new ArrayList();
		List<Student> std = new ArrayList();
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).getCin().equals(cin) && ls.get(i).getPassGenerated().equals(password)) {

				String FirstName = ls.get(i).getFirstName();
				String LastName = ls.get(i).getLastName();
                std.add(ls.get(i));
				name.add(FirstName);
				name.add(LastName);
				
				return ls.get(i);
			}
		}

		return null ;
	}

	@Override
	public FYPFile addFYPSheet(FYPFile file, long id) {

		Student std = em.find(Student.class, id);
		// List<FYPFile> ff =em.createQuery("SELECT c.fypFile.id from Student c where
		// c.id=:id").setParameter("id", id).getResultList();
		List<FYPFile> file1 = getAllStudentFileByFil(id);
		if (std != null) {

			if (file1.isEmpty()) {
				em.persist(file);
				file.setStudent(std);
				modifFyle.addFYPSheet(file);

				// em.persist(file);
				std.setFypFile(file);
				em.persist(std);

				return file;

			} else
				return file1.get(0);
		}

		return null;
	}

	@Override
	public List<Student> getAllStudentFile(long id) {

		FYPFile std = em.find(FYPFile.class, id);

		return em.createQuery("SELECT s from " + Student.class.getName() + " s  where s.fypFile.id =:id")
				.setParameter("id", id).getResultList();

	}

	@Override
	public List<FYPFile> getAllStudentFileByFil(long id) {

		List<FYPFile> f = em.createQuery("SELECT c.fypFile from Student c  where c.id=:id").setParameter("id", id)
				.getResultList();

		return f;
	}

	@Override
	public Student getStudentById(long id) {
		return em.find(Student.class, id);
	}

	@Override
	public Student editStudent(Student std) {
		return em.merge(std);
	}

	@Override
	public int removeStudent(long id) {
		Student s = em.find(Student.class, id);

		System.out.println("Debug : " + s);
		if (s != null) {

			em.remove(s);
			return 1;
		}
		return 0;
	}

	
	// fypfile

	@Override
	public List<FYPSubject> getAllCategory(long id) {
		return em.createQuery("SELECT c.subjects from FYPCategory c  where c.id=:id ")
				.setParameter("id", id).getResultList();

	}

	@Override
	public List<FYPSubject> getAllCategorys() {
		return em.createQuery("SELECT  DISTINCT(c.subjects)  from FYPCategory c ")
				.getResultList();
	}

	@Override
	public List<FYPCategory> getAllCategorysSubject() {
		return em.createQuery("SELECT  DISTINCT(c.subjects)  from FYPCategory c Join FYPSubject f  ")
				.getResultList();
	}

	@Override
	public void sendMailRec(String text, long  id) {
		// TODO Auto-generated method stub
		
		Student std = em.find(Student.class, id);
		
		Mail_API mail = new Mail_API();
		List<Student> ls = getAllStudentAutorised();
		List<Student> ls1 = getAllStudentNodisabled();
		List<Student> ls2 = getAllStudentSaved();
		List<Student> ls3 = new ArrayList();
		List<Student> ls4 = new ArrayList();

		String subject1 = "Nouvelle Reclamation de Etudiant num " + std.getCin();
		

		for (int j = 0; j < ls.size(); j++) {
			if (ls.get(j).equals(std)) {
				
				try {
					mail.sendMail(std.getEmail(), text, subject1);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
		}
	}
	/*
	 * @Override public void enregistrer(long cin) {
	 * 
	 * //Student s = em.find(Student.class, cin); //List<Student> ls = new
	 * ArrayList(); StudentEJB se = new StudentEJB(); List<Student> ls =
	 * se.em.createQuery("SELECT cin from Student s  ").getResultList();
	 * 
	 * 
	 * 
	 * for(int i=0;i<ls.size();i++) { if(se.getAllStudentCIN().get(i).getCin()==cin)
	 * {
	 * 
	 * System.out.println("this student  created"+ls.get(i));
	 * se.getAllStudentCIN().get(i).setIsSaved(true);
	 * em.persist(se.getAllStudentCIN().get(i)); em.flush();
	 * 
	 * } }
	 * 
	 * 
	 * }
	 */

	@Override
	public List<Employee> getDirectorStd(String cin) {
		List<FYPFile> ls = getAllStudentFileCin(cin);
		
		return null;
	}

	@Override
	public List<Student>  getStudentByIdAt(long id) {
		Student std = em.find(Student.class, id);
		 List<Student> ls = em.createQuery("SELECT  studyClass.name from Student c  where c.id=:id ").setParameter("id", id).getResultList();

		 return ls;
	}

	@Override
	public InternshipConvention AnnulerInter(long id) {
		InternshipConvention inter = em.find(InternshipConvention.class, id);
		inter.setCanceled(true);
		em.persist(inter);
		em.flush();
		
		
		return inter;
	}

	@Override
	public Student updateStudent(Student st) {
		return em.merge(st);
	}

}
	
