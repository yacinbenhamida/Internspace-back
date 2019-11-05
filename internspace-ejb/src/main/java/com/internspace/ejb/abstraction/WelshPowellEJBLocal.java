package com.internspace.ejb.abstraction;

import java.io.IOException;

import javax.ejb.Local;

import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.stream.GraphParseException;

@Local
public interface WelshPowellEJBLocal {
	String welsh() throws IOException, ElementNotFoundException, GraphParseException;
	String generateFYPDefenseGraph();
}
