package com.internspace.entities.fyp;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.internspace.entities.users.Student;

@Entity
@Table(name = "fyp_file_modification")
public class FYPFileModification {


	private static final long serialVersionUID = 1L;

	/*
	 * Attributes
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fyp_file_id")
	long id;
	

	String problematic;
	@Column(name = "confirmed", columnDefinition = "boolean default false")
	boolean isConfirmed;
	
	@Column(name = "changed", columnDefinition = "boolean default false")
	boolean isChanged;
	
	@Column(name = "canceled", columnDefinition = "boolean default false")
	boolean isCanceled;
	@OneToMany(mappedBy = "fypMod", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	Set<FYPFeature> features;
	
	@OneToOne
	FYPFile fyp;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProblematic() {
		return problematic;
	}

	public void setProblematic(String problematic) {
		this.problematic = problematic;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public FYPFile getFyp() {
		return fyp;
	}

	public void setFyp(FYPFile fyp) {
		this.fyp = fyp;
	}

	

	public void setFeatures(Set<FYPFeature> features) {
		this.features = features;
	}

	public Boolean getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}

	public Set<FYPFeature> getFeatures() {
		return features;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
	
	
	
}
