package com.internspace.ejb.abstraction;

import javax.ejb.Local;

import com.internspace.entities.users.User;

@Local
public interface UserEJBLocal {
	public User verifyLoginCredentials(String username, String password);
	
}
