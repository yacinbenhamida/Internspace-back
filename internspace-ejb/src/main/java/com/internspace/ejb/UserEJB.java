package com.internspace.ejb;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.UserEJBLocal;
import com.internspace.entities.users.User;
@Stateless
public class UserEJB implements UserEJBLocal{
	@PersistenceContext
	EntityManager em;
	@Override
	public User verifyLoginCredentials(String username, String password) {
		System.out.println(username + " "+password);
		Query query = em.createQuery("select u from User u where u.username = :username AND u.password = :password")
				.setParameter("username",username).setParameter("password", password);
		if(!query.getResultList().isEmpty()) {
			System.out.println("user found, authenticating");
			return (User) query.getSingleResult();
		}
		System.out.println("user not found...");
		return null;
	}

}
