package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPFeaturesEJBLocal;
import com.internspace.ejb.abstraction.FYPFileArchiveEJBLocal;
import com.internspace.ejb.abstraction.FYPFileModificationEJBLocal;
import com.internspace.ejb.abstraction.FYPSheetEJBLocal;
import com.internspace.ejb.abstraction.FYPSheetHistoryEJBLocal;
import com.internspace.ejb.abstraction.InternshipDirectorEJBLocal;
import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.entities.exchanges.Mail_API;
import com.internspace.entities.fyp.FYPFeature;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileModification;
import com.internspace.entities.fyp.FYPFile.FYPFileStatus;
import com.internspace.entities.users.Student;
@Stateless
public class FYPSheetEJB implements FYPSheetEJBLocal{
	@PersistenceContext
	EntityManager service;
	
	@Inject
	FYPSheetHistoryEJBLocal serviceHistory;
	@Inject
	StudentEJBLocal serviceStudent;
	@Inject
	InternshipDirectorEJBLocal serviceDirector;
	@Inject
	FYPFileModificationEJBLocal serviceModif;
	
	@Inject
	FYPFeaturesEJBLocal features;
	
	@Override
	public FYPFile addFYPSheet(FYPFile file) {
		service.persist(file);
		return service.find(FYPFile.class, file.getId());
	}

	@Override
	public boolean removeFYPSheet(long fypsId) {
		FYPFile target = service.find(FYPFile.class, fypsId);
		if(target!=null) {
			service.remove(target);
			service.flush();
			return true;
		}
		return false;
	}

	@Override
	public FYPFile editFYPSheet(FYPFile toEdit) {
		return service.merge(toEdit);
	}

	@Override
	public FYPFile getFYFileById(long fypfileId) {
		return (FYPFile) service.createQuery("SELECT f from FYPFile f where f.id = :id")
				.setParameter("id", fypfileId).getSingleResult();
	}

	@Override
	public FYPFile getFypFileOfStudent(long studId) {
		Query q = service.createQuery("SELECT fa from FYPFile fa, Student s where s.id = :id AND fa.id = s.fypFile.id")
				.setParameter("id", studId);
		List<Object> list = q.getResultList();
		if(!list.isEmpty()) {
			return  (FYPFile) list.get(0);
		}
		return null ;
	}

	@Override
	public List<FYPFile> getFYPfilesOfDepartment(long idDept) {
		Query q = service.createQuery("SELECT fa from FYPFile fa, Student f where "
				+ "fa.id = f.fypFile.id AND  "
				+ "f.studyClass.classOption.departement.id = :id")
				.setParameter("id", idDept);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null;
	}

	@Override
	public List<FYPFile> getAllAcceptedFYPSheets(long idDep) {
		Query q =  service.createQuery("SELECT f from FYPFile f where f.fileStatus = :status "
				+ "AND f.student.studyClass.classOption.departement.id = :id")
				.setParameter("id",idDep).setParameter("status", FYPFileStatus.confirmed);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getAllSheetsWithNoMarks() {
		Query q =   service.createQuery("SELECT f from FYPFile f where (f.finalMark = NULL OR f.finalMark = 0) AND f.fileStatus = 'confirmed' "
				+ " AND ((SELECT COUNT(i.id) FROM fyp_intervention i where (i.fypFile.id = f.id)  AND (i.givenMark = NULL OR i.givenMark = 0)) > 0 "
				+ " OR  (SELECT COUNT(i.id) FROM fyp_intervention i where i.fypFile.id = f.id) = 0)");
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getFYPSheetsWithNoSupervisors(long idDep) {
		Query q = service.createQuery("SELECT f from FYPFile f WHERE f.student.studyClass.classOption.departement.id = :id group by f.id "
				+ "HAVING (SELECT COUNT(i.id) from fyp_intervention i where i.fypFile.id = f.id AND i.teacherRole = 'supervisor' ) = 0")
				.setParameter("id", idDep);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public FYPFile getFYPSById(long id) {
		Query q =  service.createQuery("SELECT f from FYPFile f where f.id = :id")
				.setParameter("id", id);
		List<Object> list = q.getResultList();
		if(!list.isEmpty()) {
			return  (FYPFile) list.get(0);
		}
		return null ;
	}

	@Override
	public List<FYPFile> getAllSheets() {
		Query q =  service.createQuery("SELECT f from FYPFile f ");
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getFYPSheetsOfTeacher(long idTeacher) {
		Query q =  service.createQuery("SELECT f from FYPFile f group by f.id"
				+ " HAVING (SELECT COUNT(i.id) from fyp_intervention i where i.teacher.id = :idt"
				+ " AND i.fypFile.id = f.id) > 0").setParameter("idt", idTeacher);
		if(!q.getResultList().isEmpty()) {
			return  q.getResultList();
		}
		return null ;
	}

	@Override
	public List<FYPFile> getAll() {
		return service.createQuery("SELECT f from FYPFile f ").getResultList();
	}

	@Override
	public FYPFile assignFYPFileToStudent(FYPFile file, long studentId) {
		Student s = service.find(Student.class, studentId);
		s.setFypFile(file);
		service.persist(s);
		return service.find(FYPFile.class, file.getId());
	}

	
	// my work
	
	@Override
	public List<FYPFile> getAllSheetsPending() {
		
		return service.createQuery("SELECT f from FYPFile f  where f.fileStatus =:status").setParameter("status", FYPFileStatus.pending).getResultList();
	}

	
	@Override
	public FYPFileStatus etatChanged(long id) {
		
		String subject = "Votre fiche PFE est acceptée " ;
		String subject1 = "Votre fiche PFE est refusée" ;
		String text = "mail d'etat" ;
		FYPFile f = service.find(FYPFile.class, id);
		Mail_API mail = new Mail_API();
		serviceHistory.getAllFiles();
		for(int i=0;i<serviceHistory.getAllFiles().size();i++) {
			if(serviceHistory.getAllFiles().get(i).getId()!=f.getId()) {
		if(f.getFileStatus().equals(FYPFileStatus.confirmed) ||f.getFileStatus().equals(FYPFileStatus.declined)) {
			
			serviceHistory.addFYPSheet(f);
			service.persist(f);
			service.flush();
			//List<FYPFile> lc =  serviceStudent.getAllStudentFileCin(cin);
			//List<Student> ls11 = serviceStudent.getAllStudentCin(cin);
			
			
		}}
			
			
			
		}
		List<Student> lf = serviceStudent.getAllStudentFile(id);
		for(int j=0;j<lf.size();j++) {
			
			if( f.getFileStatus().equals(FYPFileStatus.confirmed) ) {
		try {
			mail.sendMail(lf.get(j).getEmail(), text, subject);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
		else if( f.getFileStatus().equals(FYPFileStatus.declined)){
			try {
				mail.sendMail(lf.get(j).getEmail(), text, subject1);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
		return f.getFileStatus();
		
	}

	@Override
	public boolean modificationMajeur(long id) {

		
		FYPFile f = service.find(FYPFile.class, id);
		
		
		
		List<FYPFile> fm =  service.createQuery("SELECT f.fyp from FYPFileModification f").getResultList();
		List<FYPFileModification> fmm= service.createQuery("SELECT f from FYPFileModification f").getResultList();

		
			for(int j=0;j<fmm.size();j++) {
				for(int i=0;i<fm.size();i++) {
					
				    if(!f.getProblematic().equals(fmm.get(j).getProblematic())) {
					
					
							fmm.get(j).setIsChanged(true);
							service.merge(fmm.get(j));
							return true;
						
				      }
			
			
		            }
	                }
		          
		
		return false;
	}
	@Override
	public FYPFile editFYPSheett(FYPFile file) {
		List<FYPFileModification> fm= service.createQuery("SELECT f from FYPFileModification f").getResultList();
		List<FYPFile> fmm= service.createQuery("SELECT f.fypFile from FYPFeature f where f.fypFile =:fypFile ").setParameter("fypFile", file).getResultList();
		 service.merge(file);
		for(int i =0 ;i<fm.size();i++){
			
			if(file.getId()==fm.get(i).getFyp().getId()) {
				for(int j =0 ;i<fmm.size();i++){
				if((file.getProblematic().equals(fm.get(i).getProblematic())) && fmm.get(j).equals(fm.get(i).getFeatures())) {
					return fm.get(i).getFyp();
				}
				else
				{
					
					fm.get(i).setProblematic(file.getProblematic());
					//fm.get(i).setFeatures();
					fm.get(i).setIsChanged(true);
					//features.addFYPFeatures(file.getFeatures());
					//file.getFeatures().iterator().next().setFypFile(file);
					//features.addFYPFeature(file.getFeatures().iterator().next());
					return fm.get(i).getFyp();
			
				}
			}
			
		}
		
	
	}
		return file;
	}

	@Override
	public List<FYPFile> allFYPfilesWatingForMarkFrom() {
		Query query = service.createQuery("SELECT f from FYPFile f, fyp_intervention interv where f.id = interv.fypFile.id AND"
				+ " interv.givenMark = 0 AND (interv.teacherRole = 'reporter' OR interv.teacherRole='reporter')");
			return query.getResultList();
	}

	@Override
	public FYPFile editFYPSheetStudent(FYPFile file, long id) {
		Student std = service.find(Student.class, id);
		file.setId(id);
		return service.merge(file);}
	

	@Override
	public FYPFile editFYPSheetStudentMaj(FYPFile file, long id) {
		// TODO Auto-generated method stub
		Student std = service.find(Student.class, id);
		file.setId(id);
		if(std.getFypFile().getId() == file.getId())
		{
			file.setId(id);
		service.merge(file);
		
		
		List<FYPFileModification> fm= service.createQuery("SELECT f from FYPFileModification f").getResultList();
		List<FYPFile> fmm= service.createQuery("SELECT f.fypFile from FYPFeature f where f.fypFile.id =:fypFile ").setParameter("fypFile", file.getId()).getResultList();
        for(int i =0 ;i<fm.size();i++){
			
		
				for(int j =0 ;i<fmm.size();i++){
				if((file.getProblematic().equals(fm.get(i).getProblematic()))){
					return file;
				}
				else
				{
					
					fm.get(i).setProblematic(file.getProblematic());
					//fm.get(i).setFeatures();
					fm.get(i).setIsChanged(true);
					//features.addFYPFeatures(file.getFeatures());
					//file.getFeatures().iterator().next().setFypFile(file);
					//features.addFYPFeature(file.getFeatures().iterator().next());
					return file;
			
				}
			}
			
		
		 
		 
		
	           }
		}
	
		

        return null;

}

	@Override
	public FYPFile acceptPFE(long id) {
		FYPFile s= service.find(FYPFile.class, id);
		if(s == null) {
			return null;}
		else {
		
		List<FYPFile> fm =  service.createQuery("SELECT f.fyp from FYPFileModification f").getResultList();
		List<FYPFileModification> fmm= service.createQuery("SELECT f from FYPFileModification f where f.fyp.id =:id").setParameter("id", id).getResultList();
		for(int j=0;j<fmm.size();j++) {
			for(int i=0;i<fm.size();i++) {
				if(fm.get(i).getId() == id) {

			    if(!s.getProblematic().equals(fmm.get(j).getProblematic())) {
				
				
						fmm.get(j).setIsChanged(true);
						
						service.persist(fmm.get(j));
						service.flush();
					
			      }
		
					
	            }
                }
		}
		
		}
		return null;
	}

	@Override
	public FYPFile accept(long id) {
		List<FYPFileModification> fmm= service.createQuery("SELECT f from FYPFileModification f where f.id =:id").setParameter("id", id).getResultList();
		for(int i=0;i<fmm.size();i++) {
			
			fmm.get(i).setIsConfirmed(true);
			fmm.get(i).getFyp().setUp(true);
			service.persist(fmm.get(i));
			service.persist(fmm.get(i).getFyp());
			service.flush();
			if(fmm.get(i).getId() == id) {
			return fmm.get(i).getFyp();}
		}
		return null;
	}

	@Override
	public FYPFile cancel(long id) {
		List<FYPFileModification> fmm= service.createQuery("SELECT f from FYPFileModification f where f.id =:id").setParameter("id", id).getResultList();
		for(int i=0;i<fmm.size();i++) {
			
			fmm.get(i).setCanceled(true);
			fmm.get(i).getFyp().setDown(true);;
			service.persist(fmm.get(i));
			service.persist(fmm.get(i).getFyp());
			service.flush();
			if(fmm.get(i).getId() == id) {
			return fmm.get(i).getFyp();}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
}
