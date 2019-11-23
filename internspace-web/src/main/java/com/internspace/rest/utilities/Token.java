package com.internspace.rest.utilities;

import java.sql.Date;

import com.internspace.entities.users.User;

public class Token {
	String hash;
	java.util.Date expiresAt;
	User user;
	public Token() {
		// TODO Auto-generated constructor stub
	}

	public Token(String hash, java.util.Date expiresAt) {
		super();
		this.hash = hash;
		this.expiresAt = expiresAt;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public java.util.Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(java.util.Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Token of user n° "+user.getId()+" [hash=" + hash + ", expiresAt=" + expiresAt + "]";
	}
	
}
