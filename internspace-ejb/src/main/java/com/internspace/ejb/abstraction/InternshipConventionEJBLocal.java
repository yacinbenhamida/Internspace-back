package com.internspace.ejb.abstraction;

import java.util.List;

import javax.ejb.Local;


import com.internspace.entities.fyp.InternshipConvention;

@Local
public interface InternshipConventionEJBLocal {
	
	public void addInternshipConvention(InternshipConvention inter);
	public  List<InternshipConvention> getAllInternshipConvention();
	public int removeConvention(long  id);

}
