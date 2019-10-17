package com.internspace.entities.fyp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "FYP_SHEET_HISTORY")
public class FYPSheetHistory implements Serializable {
	public enum TypeOfOperation{
		majorOperation,
		normalOperation
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	Date editionDate;
	@Enumerated(EnumType.STRING)
	TypeOfOperation typeOfOperation;
	int editedById;
	int oldState;
	String oldTitle;
	String oldDescription;
	String oldIssue;
	String oldMailPro;
	String oldMail;
	@ManyToOne
	FYPFile changedFile;
	public FYPSheetHistory() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getOldState() {
		return oldState;
	}

	public void setOldState(int oldState) {
		this.oldState = oldState;
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
	
	
}
