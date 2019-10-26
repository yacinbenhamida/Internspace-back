package com.internspace.services;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;

import com.internspace.ejb.abstraction.WelshPowellEJBLocal;

@Path("welshPowell")
public class WelshPowellService {

	@Inject
	WelshPowellEJBLocal welshPowellService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUniversities() {
		String result="";
		try {
			result = welshPowellService.welsh();
		} catch (ElementNotFoundException | IOException | GraphParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK)
				.entity(result).build();
		
	}
}
