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
	int oldIssue;
	int oldKw;
	int oldMailPro;
	int oldMail;
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

	public int getOldIssue() {
		return oldIssue;
	}

	public void setOldIssue(int oldIssue) {
		this.oldIssue = oldIssue;
	}

	public int getOldKw() {
		return oldKw;
	}

	public void setOldKw(int oldKw) {
		this.oldKw = oldKw;
	}

	public int getOldMailPro() {
		return oldMailPro;
	}

	public void setOldMailPro(int oldMailPro) {
		this.oldMailPro = oldMailPro;
	}

	public int getOldMail() {
		return oldMail;
	}

	public void setOldMail(int oldMail) {
		this.oldMail = oldMail;
	}

	public FYPFile getChangedFile() {
		return changedFile;
	}

	public void setChangedFile(FYPFile changedFile) {
		this.changedFile = changedFile;
	}
	
	
}
