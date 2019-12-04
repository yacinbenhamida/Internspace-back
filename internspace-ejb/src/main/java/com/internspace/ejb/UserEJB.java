package com.internspace.ejb;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.UserEJBLocal;
import com.internspace.entities.users.Employee;
import com.internspace.entities.users.Student;
import com.internspace.entities.users.User;
@Stateless
public class UserEJB implements UserEJBLocal{
	@PersistenceContext
	EntityManager em;
	@Override
	public User verifyLoginCredentials(String username, String password) {
		System.out.println("from ejb : "+username + " "+password);
		Query query = em.createQuery("select u from User u where u.username = :username AND u.password = :password")
				.setParameter("username",username).setParameter("password", password);
		if(!query.getResultList().isEmpty()) {
			
			User user = (User) query.getResultList().get(0);
			System.out.println("from ejb, user found, authenticating user with id :"+user.getId());
			return user;
		}
		System.out.println("user not found...");
		return null;
	}
	@Override
	public User getUserByUsername(String username) {
		return (User) em.createQuery("select u from User u where u.username=:us").setParameter("us", username).getResultList().get(0);
	}
	@Override
	public List<Employee> getTeachersOFdept(long idDept) {
		Query query = em.createQuery("select e from Employee e where e.department.id = :id")
				.setParameter("id",idDept);
		return query.getResultList();
	}
	@Override
	public Student getStudentOfFypSheet(long id) {
		return (Student) em.createQuery("select s from Student s where s.fypFile.id = :id").setParameter("id", id).getResultList().get(0);
	}
	@Override
	public User getUserById(long id) {
		return (User) em.createQuery("select u from User u where u.id=:us").setParameter("us", id).getResultList().get(0);
	}
}
