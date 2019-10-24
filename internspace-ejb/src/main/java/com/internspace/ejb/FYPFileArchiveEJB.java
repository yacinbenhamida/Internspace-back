package com.internspace.ejb;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.FYPFileArchiveEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileArchive;

public class FYPFileArchiveEJB implements FYPFileArchiveEJBLocal{

	@PersistenceContext
	EntityManager em;

	@Override
	public void addFileToArchive(FYPFile f) {
		FYPFileArchive Farchive = new FYPFileArchive() ;
		Farchive.setFypFile(f);
		Farchive.setArchiveDate(new Date());
		em.persist(Farchive);
		em.flush();
		System.out.println("fichier ajouter a l'archive");
	}

	@Override
	public void DeleteFileToArchive(long id) {
		FYPFileArchive fa = em.find(FYPFileArchive.class, id);
		if(fa != null)
			em.remove(fa);
		else
			System.out.println("fichier n'existe pas");
	}

	@Override
	public void UpdateFileToArchive(FYPFileArchive f) {
		em.merge(f);
		System.out.println("le fichier a été mis à jour");
		
	}

	@Override
	public List<FYPFileArchive> ListOfArchivedFile() {
		return em.createQuery("SELECT fa FROM FYPFileArchive fa").getResultList();
		
	}
}
