package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

import model.Graph;
import model.GraphStochastique;
import sun.security.provider.certpath.Vertex;

public class VNS {
	public static GraphStochastique solve(GraphStochastique graphStochastiqueEntree){
		GraphStochastique solution = new GraphStochastique(graphStochastiqueEntree.getNbrVertex());
		GraphStochastique graphStochastiqueTMP = new GraphStochastique(graphStochastiqueEntree);
		
		// ----------  Debut Resolution    ----------
		
		// Veillez traivallez avec le variable local => " graphStochastiqueTMP "
		
		// Algorithme VNS ICI
		
		// ----------  Debut Resolution    ----------
		
		return solution;
	}
}
