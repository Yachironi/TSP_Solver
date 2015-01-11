package controller;

import java.util.ArrayList;

import model.GraphStochastique;

public class Glouton {

	public static GraphStochastique solve(GraphStochastique g) {
		if (g == null)
			return null;
		GraphStochastique graphSolution = new GraphStochastique(
				g.getNbrVertex());
		Integer departGlouton = (int) (Math.random() * g.getNbrVertex());
		//System.out.println("Depart Glouton= " + departGlouton);
		boolean condition = true;
		Integer depart = departGlouton;
		Integer arrive = -1;
		ArrayList<Integer> villeParcouru = new ArrayList<Integer>();

		while (!depart.equals(arrive)) {
			depart = arrive == -1 ? depart : arrive;
			arrive = getBestSuiv(g, depart, villeParcouru, departGlouton);
			villeParcouru.add(arrive);
			graphSolution.setEdgeCost(depart, arrive,
					g.getEdgeCost(depart, arrive));
		}
		graphSolution
				.setEdgeCost(depart, arrive, g.getEdgeCost(depart, arrive));
		return graphSolution;
	}

	private static Integer getBestSuiv(GraphStochastique g, Integer departTMP,
			ArrayList<Integer> villeParcouru, Integer departGlouton) {
		// TODO Stub de la méthode généré automatiquement
		Double minVal = Double.MAX_VALUE;
		Integer minVertex = null;
		for (int i = 0; i < g.getNbrVertex(); i++) {
			if ((g.getEdgeCost(departTMP, i) != Double.MAX_VALUE)
					&& !villeParcouru.contains(i) && i != departGlouton) {
				if (g.getEdgeCost(departTMP, i) <= minVal) {
					minVal = g.getEdgeCost(departTMP, i);
					minVertex = i;
				}
			}
		}
		if (minVertex == null) {
			return departGlouton;
		}
		return minVertex;
	}
}
