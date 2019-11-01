package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.internspace.entities.fyp.FYPFile.FYPFileStatus;

@Entity(name = "fyp_sheet_history")
public class FYPSheetHistory implements Serializable {
	public enum TypeOfOperation {
		majorOperation, normalOperation
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	Date editionDate;

	@Enumerated(EnumType.STRING)
	TypeOfOperation typeOfOperation;

	int editedById; // Modifier might be a teacher, student...
	int oldCompanyId;
	FYPFileStatus oldState;

	String oldTitle;
	String oldDescription;
	String oldIssue;
	String oldMailPro;
	String oldMail;
	String oldKeywords;
	String oldSubject;

	@ManyToOne
	@JoinColumn(name = "changed_file_id")
	FYPFile changedFile;

	public FYPSheetHistory() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getEditionDate() {
		return editionDate;
	}

	public void setEditionDate(Date editionDate) {
		this.editionDate = editionDate;
	}

	public TypeOfOperation getTypeOfOperation() {
		return typeOfOperation;
	}

	public void setTypeOfOperation(TypeOfOperation typeOfOperation) {
		this.typeOfOperation = typeOfOperation;
	}

	public int getEditedById() {
		return editedById;
	}

	public void setEditedById(int editedById) {
		this.editedById = editedById;
	}

	public FYPFileStatus getOldState() {
		return oldState;
	}

	public void setOldState(FYPFileStatus fypFileStatus) {
		this.oldState = fypFileStatus;
	}

	public String getOldTitle() {
		return oldTitle;
	}

	public void setOldTitle(String oldTitle) {
		this.oldTitle = oldTitle;
	}

	public String getOldDescription() {
		return oldDescription;
	}

	public void setOldDescription(String oldDescription) {
		this.oldDescription = oldDescription;
	}

	public String getOldIssue() {
		return oldIssue;
	}

	public void setOldIssue(String oldIssue) {
		this.oldIssue = oldIssue;
	}

	public String getOldMailPro() {
		return oldMailPro;
	}

	public void setOldMailPro(String oldMailPro) {
		this.oldMailPro = oldMailPro;
	}

	public String getOldMail() {
		return oldMail;
	}

	public void setOldMail(String oldMail) {
		this.oldMail = oldMail;
	}

	public FYPFile getChangedFile() {
		return changedFile;
	}

	public void setChangedFile(FYPFile changedFile) {
		this.changedFile = changedFile;
	}

	public String getOldKeywords() {
		return oldKeywords;
	}

	public void setOldKeywords(String oldKeywords) {
		this.oldKeywords = oldKeywords;
	}

	public String getOldSubject() {
		return oldSubject;
	}

	public void setOldSubject(String oldSubject) {
		this.oldSubject = oldSubject;
	}

	public int getOldCompanyId() {
		return oldCompanyId;
	}

	public void setOldCompanyId(int oldCompanyId) {
		this.oldCompanyId = oldCompanyId;
	}

}
