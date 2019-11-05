package com.internspace.ejb.abstraction;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.internspace.entities.fyp.FYPDefense;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.university.Classroom;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;

public interface FYPDefenseEJBLocal {
	List<FYPIntervention> getDefenseInterventions(long idDefense);
	List<Integer> getDefenseReporterAndSupervisorID(long idDefense);
	String generateDefensesGraph(List<FYPDefense> defensesList);
	String getColoredDefensesGraph(String defensesGraph) throws IOException, ElementNotFoundException, GraphParseException;
	void getDefensesPlanning(Date day, List<FYPDefense> defensesList, List<Classroom> classRoomsList);
}
