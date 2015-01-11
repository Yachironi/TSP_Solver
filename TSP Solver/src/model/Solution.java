package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import util.Paire;

public class Solution implements Cloneable{

	ArrayList<Paire<Integer, Integer>> arcs;
	GraphStochastique graphEntree;
	public Object clone() {
		Solution solution = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la
			// méthode super.clone()
			solution = (Solution) super.clone();
		} catch (CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}

		// On clone l'attribut de type Patronyme qui n'est pas immuable.
		solution.arcs = (ArrayList<Paire<Integer, Integer>>) arcs.clone();
		//solution.graphEntree= (GraphStochastique)graphEntree.clone();
		return solution;
	}
	public Solution() {
		super();
		arcs=new ArrayList<Paire<Integer, Integer>>();
	}


	public GraphStochastique getGraphEntree() {
		return graphEntree;
	}

	public void setGraphEntree(GraphStochastique graphEntree) {
		this.graphEntree = graphEntree;
	}
	
	public Paire<Integer,Integer> getArcSortant(Integer x){
		for (Iterator iterator = arcs.iterator(); iterator.hasNext();) {
			Paire<Integer, Integer> paire = (Paire<Integer, Integer>) iterator
					.next();
			if (paire.getSecond().equals(x)){
				return paire; 
			}
		}
		return null;
	}

	public Solution(GraphStochastique g) {
		super();
		arcs = new ArrayList<Paire<Integer, Integer>>();
		graphEntree = (GraphStochastique) graphEntree.clone();	
	}

	public Solution  graphToSolution(GraphStochastique g){
		Solution solutionGraph=new Solution();
		graphEntree =new GraphStochastique(g.getNbrVertex());
		for (int i = 0; i < g.getNbrVertex(); i++) {
			for (int j = 0; j < g.getNbrVertex(); j++) {
				if (!g.getEdgeCost(i, j).equals(Double.MAX_VALUE)) {
					solutionGraph.getArcs().add(new Paire<Integer, Integer>(i, j));
				}
			}
		}
		 
		return solutionGraph;
	}
	
	public Solution(ArrayList<Paire<Integer, Integer>> arcs) {
		super();
		this.arcs = arcs;
	}

	public void setArc(Integer i, Integer j, Paire<Integer, Integer> arc) {
		if (arcs.contains(new Paire<Integer, Integer>(i, j))) {
			arcs.set(arcs.indexOf(new Paire<Integer, Integer>(i, j)), arc);
		}
	}

	public ArrayList<Paire<Integer, Integer>> getArcs() {
		return arcs;
	}

	public void setArcs(ArrayList<Paire<Integer, Integer>> arcs) {
		this.arcs = arcs;
	}

	public double getTotalCost(GraphStochastique graphEntree2) {
		Double cost = 0.0;
		for (Iterator iterator = arcs.iterator(); iterator.hasNext();) {
			Paire<Integer, Integer> paire = (Paire<Integer, Integer>) iterator.next();
			cost = cost	+ graphEntree2.getEdgeCost(paire.getFirst(),paire.getSecond());
		}
		return cost;
	}

	@Override
	public String toString() {
		return "Solution [arcs=" + arcs + "]";
	}
}
