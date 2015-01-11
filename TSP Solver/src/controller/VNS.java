package controller;

import java.util.ArrayList;

import model.GraphStochastique;
import model.Solution;
import util.Paire;

public class VNS {
	public static GraphStochastique solve(
			GraphStochastique graphStochastiqueEntree) {
		GraphStochastique solution = new GraphStochastique(
				graphStochastiqueEntree.getNbrVertex());
		GraphStochastique graphStochastiqueTMP = new GraphStochastique(
				graphStochastiqueEntree);

		// ---------- Debut Resolution ----------

		// Veillez traivallez avec le variable local => " graphStochastiqueTMP "

		int k = 0;
		int k_max = 10;
		int t = 0;
		int t_max = 5;

		GraphStochastique sol = new GraphStochastique(
				graphStochastiqueEntree.getNbrVertex());
		GraphStochastique sol1 = new GraphStochastique(
				graphStochastiqueEntree.getNbrVertex());
		GraphStochastique sol2 = new GraphStochastique(
				graphStochastiqueEntree.getNbrVertex());

		sol = new GraphStochastique(Glouton.solve(graphStochastiqueEntree));
		Double meilleur = Double.MAX_VALUE;
		rechercheMeilleurSolution(sol,2);
		/*while (t <= t_max) {
			k = 1;
			while (k <= k_max) {
				t++;
				//sol1 = shake(sol, k); // Glouton
				//sol2 = recherchLocal(sol1, k);
				sol1 = rechercheMeilleurSolution(sol,2);
				if (fObj(sol2) < meilleur) {
					sol = new GraphStochastique(sol2);
					k = 1;
				} else {
					k = k + 1;
				}
				// sol1 = NeighbourhoodChange(sol1,sol2,k);
			}
		}*/
		// ---------- Debut Resolution ----------

		return solution;
	}

	private static GraphStochastique rechercheMeilleurSolution(
			GraphStochastique sol, int i) {
		Solution solutionEntree = new Solution(sol);
		System.out.println(solutionEntree+" K = "+i);
		return null;
	}

	private static Double fObj(GraphStochastique sol2) {
		// TODO Stub de la méthode généré automatiquement

		return null;
	}

	private static GraphStochastique recherchLocal(GraphStochastique sol1, int k) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	private static GraphStochastique shake(GraphStochastique sol, int k) {
		ArrayList<Paire<Integer, Integer>> possible = new ArrayList<Paire<Integer, Integer>>();
		ArrayList<Paire<Integer, Integer>> interdit = new ArrayList<Paire<Integer, Integer>>();
		Integer depart = null;
		Integer arrive = null;
		// Generation des k_arcs à éléminer
		for (int i = 0; i <= k; i++) {
			while (interdit.contains(new Paire<Integer, Integer>(depart, arrive))) {
				depart = (int) (Math.random() * sol.getNbrVertex());
				arrive = (int) (Math.random() * sol.getNbrVertex());
				while (depart.equals(arrive)) {
					arrive = (int) (Math.random() * sol.getNbrVertex());
				}
			}
			interdit.add(new Paire<Integer, Integer>(depart, arrive));
		}
		// Elemination des cas inutile
		for(int i=0;i<sol.getNbrVertex();i++){
			for(int j=0;j<sol.getNbrVertex();j++){
				if(sol.isEdgeExist(i, j)&&!interdit.contains(new Paire<Integer,Integer>(i,j))){
					for(int c=0;c<sol.getNbrVertex();c++){
						
						// if a->b existe => intedit tout(c)->b 
						if(!sol.isEdgeExist(c, i)){
						interdit.add(new Paire<Integer,Integer>(c,i));
						}
						if(!sol.isEdgeExist(j, c)){
						if(c!=i){
						// if a->b existe => intedit tout(c)->b
							interdit.add(new Paire<Integer,Integer>(j,c));
						}
					}
				}
			}
		}
		
		
		return null;
	}
		return sol;
}}
