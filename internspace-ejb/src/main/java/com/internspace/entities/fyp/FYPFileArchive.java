package com.internspace.entities.fyp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="fyp_file_archive")
public class FYPFileArchive extends FYPFile {
	private static final long serialVersionUID = 1L;
	
	@Column(name="archive_Date")
	Date archiveDate;
	
	

	public FYPFileArchive() {
		super();
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
	
}


