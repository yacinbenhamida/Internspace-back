package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;

import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileArchive;

@Local
public interface FYPFileArchiveEJBLocal {

	public void addFileToArchive(FYPFile f);
	public void DeleteFileToArchive(long id);
	public void UpdateFileToArchive(FYPFileArchive f);
	public List<FYPFileArchive> ListOfArchivedFile ();
}
