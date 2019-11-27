package com.internspace.ejb.abstraction;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.internspace.entities.fyp.FYPDefense;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.university.Classroom;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;

public interface FYPDefenseEJBLocal {
	List<FYPIntervention> getDefenseInterventions(long idDefense);
	List<Integer> getDefenseReporterAndSupervisorID(long idDefense);
	String generateDefensesGraph(List<FYPDefense> defensesList);
	Map getColoredDefensesGraph(String defensesGraph) throws IOException, ElementNotFoundException, GraphParseException;
	void getDefensesPlanning(List<FYPDefense> defensesList, List<String> classRoomsList);
}
