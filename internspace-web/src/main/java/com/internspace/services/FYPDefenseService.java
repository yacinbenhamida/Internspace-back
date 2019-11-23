package com.internspace.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.internspace.ejb.abstraction.FYPDefenseEJBLocal;
import com.internspace.ejb.abstraction.WelshPowellEJBLocal;
import com.internspace.entities.fyp.FYPDefense;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;

@Path("defense")
public class FYPDefenseService {
	@Inject
	FYPDefenseEJBLocal defenseService;
	WelshPowellEJBLocal welshPowellService;

	@GET
	@Path("{id}/interventions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDefenseInterventions(@PathParam(value = "id") long id) {
		return Response.status(Response.Status.OK).entity(defenseService.getDefenseInterventions(id)).build();

	}

	@GET
	@Path("{id}/reporter-supervisor")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDefenseReporterAndSupervisor(@PathParam(value = "id") long id) {
		return Response.status(Response.Status.OK).entity(defenseService.getDefenseReporterAndSupervisorID(id)).build();

	}

	@GET
	@Path("generateGraph")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateDefensesGraph(List<FYPDefense> listDefenses) {
		return Response.status(Response.Status.OK).entity(defenseService.generateDefensesGraph(listDefenses)).build();

	}

	@GET
	@Path("generateDefensesColoredGraph")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response generateDefensesColoredGraph(List<FYPDefense> listDefenses) {
		String defensesGraph = defenseService.generateDefensesGraph(listDefenses);
		String coloredDefensesGraph = "";
		try {
			coloredDefensesGraph = defenseService.getColoredDefensesGraph(defensesGraph).toString();
		} catch (ElementNotFoundException | IOException | GraphParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK)
				.entity(coloredDefensesGraph).build();
		
	}

	@GET
	@Path("getDefensesPlanning")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDefensesPlanning(List<FYPDefense> defensesList) {
		List<String> classRoomsList = new ArrayList<String>();
		classRoomsList.add("A1");
		classRoomsList.add("A2");
		defenseService.getDefensesPlanning(defensesList,classRoomsList);
		return Response.status(Response.Status.OK)
		.entity("Updated Planning").build();

	}
}
