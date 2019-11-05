package com.internspace.ejb;

import java.io.IOException;
import java.io.StringReader;

import javax.ejb.Stateless;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceDGS;

import com.internspace.ejb.abstraction.WelshPowellEJBLocal;

@Stateless
public class WelshPowellEJB implements WelshPowellEJBLocal{
	
    //     B-(1)-C
	//    /       \
	//  (1)       (10)
	//  /           \
	// A             F
	//  \           /
	//  (1)       (1)
	//    \       /
	//     D-(1)-E
	static String my_graph = 
		"DGS004\n" 
		+ "my 0 0\n" 
		+ "an A \n" 
		+ "an B \n"
		+ "an C \n"
		+ "an D \n"
		+ "an E \n"
		+ "an F \n"
		+ "ae AB A B weight:1 \n"
		+ "ae AD A D weight:1 \n"
		+ "ae BC B C weight:1 \n"
		+ "ae CF C F weight:10 \n"
		+ "ae DE D E weight:1 \n"
		+ "ae EF E F weight:1 \n"
		;
		/*static String my_graph = 
		"DGS004\n" 
		+ "my 0 0\n" 
		+ "an 11 \n" 
		+ "an 2 \n"
		+ "an 3 \n"
		+ "an D \n"
		+ "an E \n"
		+ "an F \n"
		+ "ae 112 11 2 weight:1 \n"
		+ "ae 11D 11 D weight:1 \n"
		+ "ae 23 2 3 weight:1 \n"
		+ "ae 3F 3 F weight:10 \n"
		+ "ae DE D E weight:1 \n"
		+ "ae EF E F weight:1 \n"
		;*/
	@Override
	public String welsh() throws IOException, ElementNotFoundException, GraphParseException {
		Graph graph = new DefaultGraph("Welsh Powell Test");
		StringReader reader  = new StringReader(my_graph);
		
		FileSourceDGS source = new FileSourceDGS();
		source.addSink(graph);
		source.readAll(reader);
		
		WelshPowell wp = new WelshPowell("color");
		wp.init(graph);
		wp.compute();
		
		System.out.println("The chromatic number of this graph is : "+wp.getChromaticNumber());
		String result="";
		for(Node n : graph){
			//System.out.println("Node "+n.getId()+ " : color " +n.getAttribute("color"));
			result += "Node "+n.getId()+ " : color " +n.getAttribute("color");
		}
		return result;
	}
	@Override
	public String generateFYPDefenseGraph() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
