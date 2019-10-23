package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * Gets created when a FYPFile student's cancelation is accepted
 */
@Entity
@Table(name = "fyp_file_archive")
public class FYPFileArchive implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fyp_file_archive_id")
	long id;

	@ManyToOne
	@JoinColumn(name = "fyp_file")
	FYPFile fypFile;

	@Column(name = "archive_Date")
	Date archiveDate;

	public FYPFileArchive() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FYPFile getFypFile() {
		return fypFile;
	}

	public void setFypFile(FYPFile fypFile) {
		this.fypFile = fypFile;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}
}
