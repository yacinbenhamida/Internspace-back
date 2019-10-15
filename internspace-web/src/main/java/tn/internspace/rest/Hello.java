package tn.internspace.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hi")
public class Hello {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "the first deepcode WS";
	}
	@GET
	@Path("sample")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello2() {
		return "the first deepcode WS";
	}
}
