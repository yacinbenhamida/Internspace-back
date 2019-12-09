package com.internspace.rest.utilities;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.internspace.ejb.abstraction.StudentEJBLocal;
import com.internspace.ejb.abstraction.UserEJBLocal;
import com.internspace.entities.users.Student;
import com.internspace.entities.users.User;
import com.internspace.entities.users.User.UserType;

import java.time.ZoneId;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Path("authentication")
public class AuthenticationEndPoint {

	// ======================================
	// = Injection Points =
	// ======================================

	@Context
	private UriInfo uriInfo;
	
	@EJB
	UserEJBLocal userService;
	@EJB
	StudentEJBLocal studentService;
	
	User connectedUser = new User();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//// this is a first alternative (with formParam)
	public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {
		//// this is a second alternative (with the Credentials class)
		// public Response authenticateUser(Credentials cred) {
		try {
			// Authenticate the user using the credentials provided
			int found = authenticate(username, password);
			// authenticate(cred.getUsername(), cred.getPassword());
			if(found == 1) {
				// Issue a token for the user
				Token token = issueToken(username);
				// String token = issueToken(cred.getUsername());
				// Return the token on the response
				return Response.ok(token).build();
			}
			return Response.status(Status.UNAUTHORIZED).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

	private int authenticate(String username, String password) {
		// Authenticate against a database, LDAP, file or whatever
		// Throw an Exception if the credentials are invalid
		System.out.println("From WS Authenticating user...");
		if(userService.verifyLoginCredentials(username, password) != null) {
			connectedUser = userService.verifyLoginCredentials(username, password);
			System.out.println(connectedUser);
			if(connectedUser.getUserType().equals(UserType.student)) {
				Student stud = studentService.getStudentById(connectedUser.getId());
				if(stud.getIsAutorised() == false || stud.getIsDisabled() == true ) {
					System.out.println("student isnt allowed to login (disabled or unauthorized)");
					return 0;
				}	
				System.out.println("ws is here ");
				return 1;
			}
			System.out.println("logged On now : is a "+connectedUser.getUserType()+" : "+connectedUser);
			return 1;
		}
		System.out.println("Auth failed...");
		return 0;

	}

	private Token issueToken(String username) {
		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token

		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
		System.out.println("the key is : " + key.hashCode());

		System.out.println("uriInfo.getAbsolutePath().toString() : " + uriInfo.getAbsolutePath().toString());
		System.out.println("Expiration date: " + toDate(LocalDateTime.now().plusMinutes(120L)));

		String jwtToken = Jwts.builder().setSubject(username).setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusMinutes(120L)))
				.signWith(SignatureAlgorithm.HS512, key).compact();
		Token result = new Token(jwtToken, toDate(LocalDateTime.now().plusMinutes(120L)));
		result.setUser(userService.getUserByUsername(username));
		System.out.println("the returned token is : " + jwtToken);
		System.out.println("the fully updated token is "+result);
		
		return result;
	}

	// ======================================
	// = Private methods =
	// ======================================

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
