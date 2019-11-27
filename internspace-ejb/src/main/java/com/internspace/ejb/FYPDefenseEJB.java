package com.internspace.ejb;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.internspace.ejb.abstraction.FYPDefenseEJBLocal;
import com.internspace.entities.fyp.FYPDefense;
import com.internspace.entities.fyp.FYPFile;
import com.internspace.entities.fyp.FYPIntervention;
import com.internspace.entities.university.Classroom;
import com.internspace.entities.users.Employee;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSourceDGS;

@Stateless
public class FYPDefenseEJB implements FYPDefenseEJBLocal {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<FYPIntervention> getDefenseInterventions(long idDefense) {
		FYPDefense defense = em.find(FYPDefense.class, idDefense);
		long idFYPfile = defense.getFyp_file_defense().getId();
		List<FYPIntervention> listInterventions = em
				.createQuery("SELECT i from fyp_intervention i WHERE i.fypFile.id=" + idFYPfile).getResultList();
		return listInterventions;
	}

	@Override
	public List<Integer> getDefenseReporterAndSupervisorID(long idDefense) {
		FYPDefense defense = em.find(FYPDefense.class, idDefense);
		long idFYPfile = defense.getFyp_file_defense().getId();
		List<Integer> listReporterAndSupervisor = em.createQuery("SELECT i.teacher.id from fyp_intervention i "
				+ "WHERE (i.fypFile.id=" + idFYPfile + ") AND (i.teacherRole='reporter' OR i.teacherRole='supervisor')")
				.getResultList();
		return listReporterAndSupervisor;
	}

	@Override
	public String generateDefensesGraph(List<FYPDefense> defensesList) {
		Integer countDefenses = defensesList.size();

		String my_graph = "DGS004\n" + "my 0 0\n";
		List<Long> defensesIdList = new ArrayList<>();
		Map map = new HashMap<Integer, List<Integer>>();
		Integer currentDefenseId = 0;
		for (FYPDefense d : defensesList) {
			currentDefenseId = (int) d.getId();
			map.put(currentDefenseId, getDefenseReporterAndSupervisorID(currentDefenseId));
			my_graph += "an " + currentDefenseId.toString() + " \n";
			defensesIdList.add((long) currentDefenseId);
		}
		System.out.println("DEBUGGING MAP:");
		System.out.println(map.toString());
		Integer i, j;
		i = 0;
		j = 0;
		for (Object x : map.values()) {
			List<Long> a = (List<Long>) x;
			System.out.println("Phase " + x.toString());
			for (Object y : map.values()) {
				if (j <= i) {
					j++;
					continue;
				}
				List<Long> b = (List<Long>) y;
				// System.out.println("Comparing : "+x.toString()+" and "+y.toString());
				List<Long> c = b.stream().filter(a::contains).collect(Collectors.toList());
				if (c.size() > 0) {
					my_graph += "ae " + defensesIdList.get(i) + defensesIdList.get(j) + " " + defensesIdList.get(i)
							+ " " + defensesIdList.get(j) + " weight:1 \n";
				}
				// System.out.println("Comment Elements : "+c.toString());
				j++;
			}
			j = 0;
			i++;

		}
		System.out.println(my_graph);
		return my_graph;
	}

	@Override
	public Map getColoredDefensesGraph(String defensesGraph)
			throws IOException, ElementNotFoundException, GraphParseException {
		Graph graph = new DefaultGraph("Welsh Powell Test");
		StringReader reader = new StringReader(defensesGraph);

		FileSourceDGS source = new FileSourceDGS();
		source.addSink(graph);
		source.readAll(reader);

		WelshPowell wp = new WelshPowell("color");
		wp.init(graph);
		wp.compute();
		System.out.println("The chromatic number of this graph is : " + wp.getChromaticNumber());
		String result = "";
		Map map = new HashMap<String, List<String>>(); // Key: ColorId, List: DefensesId
		for (Node n : graph) {
			// System.out.println("Node "+n.getId()+ " : color " +n.getAttribute("color"));
			result += "Node " + n.getId() + " : color " + n.getAttribute("color");
			if (!map.containsKey(n.getAttribute("color"))) {
				List<String> x = new ArrayList<String>();
				x.add(n.getId());
				map.put(n.getAttribute("color"), x);
			} else {
				List<String> x = (List<String>) map.get(n.getAttribute("color"));
				x.add(n.getId());
				map.put(n.getAttribute("color"), x);
			}
			// System.out.println(map);
		}
		return map;
	}

	@Override
	public void getDefensesPlanning(List<FYPDefense> defensesList, List<String> classRoomsList) {
		String defensesGraph = generateDefensesGraph(defensesList);
		Map map = null;
		try {
			map = getColoredDefensesGraph(defensesGraph);
		} catch (ElementNotFoundException | IOException | GraphParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = (Date) format.parse("2019-11-6 09:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		int j=9;
		for(Object key:map.values() ) {
			List<String> x = (List<String>) key;
			for(String s : x) {
				try {
					date = (Date) format.parse("2019-11-6 "+j+":00:00");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Query q = em.createQuery("UPDATE FYPDefense d SET d.defenseDate =:dateDefense, d.classroom=:classroom WHERE d.id =:id");
				q.setParameter("dateDefense", date);
				q.setParameter("classroom", classRoomsList.get(i));
				q.setParameter("id", Long.parseLong(s));
				q.executeUpdate();
				i++;
			}
			i=0;
			j++;

		}



	}

}
