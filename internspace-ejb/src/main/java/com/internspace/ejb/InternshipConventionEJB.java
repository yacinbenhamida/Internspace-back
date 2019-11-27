package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.internspace.ejb.abstraction.InternshipConventionEJBLocal;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.InternshipConvention;
import com.internspace.entities.university.University;
import com.internspace.entities.users.Student;

@Stateless
public class InternshipConventionEJB implements InternshipConventionEJBLocal {

	@PersistenceContext
	EntityManager em;

	@Override
	public void addInternshipConvention(InternshipConvention inter, long id) {

		Student std = em.find(Student.class, id);

		/*
		 * String queryStr = "SELECT FROM IC " + InternshipConvention.class.getName() +
		 * " WHERE" + " IC.student.id =: studentId";
		 * 
		 * List<InternshipConvention> ics =
		 * em.createQuery(queryStr).setParameter("studentId", id).getResultList();
		 * 
		 * 
		 * // If an internship convention of this student exists then leave. if(ics ==
		 * null )
		 */

		List<InternshipConvention> ics = em.createQuery(
				"SELECT SI FROM " + InternshipConvention.class.getName() + " SI WHERE  SI.student.id = :studentId")
				.setParameter("studentId", id).getResultList();
		if (ics == null )
			return;
          inter.setStudent(std);
		em.persist(inter);

	}

	@Override
	public List<InternshipConvention> getAllInternshipConvention() {

		return em.createQuery("SELECT c from InternshipConvention c").getResultList();

	}

	@Override
	public int removeConvention(long id) {

		InternshipConvention u = em.find(InternshipConvention.class, id);

		System.out.println("Debug : " + u);
		if (u != null) {

			em.remove(u);
			return 1;
		}
		return 0;

	}

	@Override
	public List<Student> getFypConventionStudent(long id) {
		Student std = em.find(Student.class, id);

		return em.createQuery("SELECT s from " + InternshipConvention.class.getName() + " s  where s.student.id =:id")
				.setParameter("id", std.getId()).getResultList();
	}

}
