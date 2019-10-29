package com.internspace.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.internspace.ejb.abstraction.FYPFileArchiveEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPFileArchive;
import com.internspace.entities.users.Student;
@Path("ArchiveFile")
@Stateless
public class FYPFileArchiveService {

	@Inject
	FYPFileArchiveEJBLocal service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listFile")
	public List<FYPFileArchive> getArchivedFiles(){
		return service.ListOfArchivedFile();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("addToArchive")
	public void  addFileToArchive(FYPFile f){
		service.addFileToArchive(f);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("deleteArchive/{id}")
	public void  deleteArchive(long id){
		service.DeleteFileToArchive(id);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("updateArchive")
	public void  updateArchive(FYPFileArchive f){
		service.UpdateFileToArchive(f);
	}
}
