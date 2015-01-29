package controller.ps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import model.GraphStochastique;
import model.Solution;
import util.Paire;
import controller.Glouton;

public class VNS {
	int k;
	int kMax;
	int itMax;
	GraphStochastique graphEntree;
	Solution solution;

	public VNS(int kMax, int itMax, GraphStochastique graphEntree) {
		super();
		this.kMax = kMax;
		this.itMax = itMax;
		this.graphEntree = (GraphStochastique) graphEntree.clone();
		this.solution = new Solution();
		this.solution = solution.graphToSolution(Glouton.solve(graphEntree));
	}

	private static Paire<Integer, Integer> getRandomPair(
			ArrayList<Paire<Integer, Integer>> interdit, GraphStochastique g,
			Integer x) {
		Integer arrive = null;
		int i = 0;
		do {
			//System.out.println("Rand " + (i++));
			arrive = (int) (Math.random() * g.getNbrVertex());
			while (arrive.equals(x)) {
				arrive = (int) (Math.random() * g.getNbrVertex());
			}
		} while (interdit.contains(new Paire<Integer, Integer>(x, arrive)));

		return new Paire<Integer, Integer>(x, arrive);
	}

	public Solution solve(int itMax, int kMax) {
		int it = 0, k = 2;

		this.kMax = kMax;
		this.itMax = itMax;
		Solution s1 = solution.graphToSolution(Glouton.solve(graphEntree));// new
																			// Solution(Glouton.solve(graphEntree));
		// solution = solution.graphToSolution(Glouton.solve(graphEntree));
		Solution s2 = new Solution();
		while (k <= kMax) {
			//System.out.println("Pour k = " + k);
			while (it <= itMax) {
				ArrayList<Paire<Integer, Integer>> edgeAEnleve = shaking(k);
				//System.out.println("Iteration = "+it);
				//System.out.println(edgeAEnleve);
				s2 = localSearch(solution, edgeAEnleve, k);
				solution = (Solution) s2.clone();
				//System.out.println(s2);
				//System.out.println("Cout Total = "+s2.getTotalCost(graphEntree));

				if (s2.getTotalCost(graphEntree) < s1.getTotalCost(graphEntree)) {
					s1 = (Solution) s2.clone();
					solution = (Solution) s2.clone();
					// System.out.println("Meilleur Solution Trouve !!! ");
					// System.out.println("Iteration = "+it);
					// System.out.println("Cout Total = "+s1.getTotalCost(graphEntree));
				}
				it++;
			}
			k++;
			it = 0;
		}

		/*
		 * while (k < kMax) { while (it < itMax) { ArrayList<Paire<Integer,
		 * Integer>> edgeAEnleve = shaking(k + 1);
		 * localSearch(solution,edgeAEnleve,k+1); // showPath(solution); it++; }
		 * k++; it = 0; }
		 */
		return s1;
	}

	private Solution localSearch(Solution solution,
			ArrayList<Paire<Integer, Integer>> edgeAEnleve, int k) {
		Solution solutionTMP = (Solution) solution.clone();
		if (k > 2) {
			// TODO N-OPT
			//System.out.println(solutionTMP);
			//System.out.println("Nbr d'arc avant la recherche "+solutionTMP.getArcs().size());
			
			Paire<Integer, Integer> pairAajouter = new Paire<Integer, Integer>();
			ArrayList<Paire<Integer, Integer>> listAajouter = new ArrayList<Paire<Integer,Integer>>();
			// Ajout Premier arc enlevé
			pairAajouter = new Paire<Integer, Integer>();
			pairAajouter.setFirst(edgeAEnleve.get(0).getFirst());
			pairAajouter.setSecond(edgeAEnleve.get(1).getSecond());
			//System.out.println(pairAajouter);
			listAajouter.add(pairAajouter);
			//  Ajout Deuxième arc enlevé 
			pairAajouter = new Paire<Integer, Integer>();
			pairAajouter.setFirst(edgeAEnleve.get(0).getSecond());
			pairAajouter.setSecond(edgeAEnleve.get(2).getFirst());
			//System.out.println(pairAajouter);
			listAajouter.add(pairAajouter);
			pairAajouter = new Paire<Integer, Integer>();
			//  Ajout Dernier arc enlevé 
			pairAajouter.setFirst(edgeAEnleve.get(edgeAEnleve.size()-1).getSecond());
			pairAajouter.setSecond(edgeAEnleve.get(edgeAEnleve.size()-2).getFirst());
			//System.out.println(pairAajouter);
			listAajouter.add(pairAajouter);
			
		
			
			//System.out.println("Nbr d'arc après le remove "+solutionTMP.getArcs().size());
			for(int i=1;i<edgeAEnleve.size()-2;i++){
				pairAajouter = new Paire<Integer, Integer>();
				pairAajouter = new Paire<Integer, Integer>();
				pairAajouter.setFirst(edgeAEnleve.get(i).getSecond());
				pairAajouter.setSecond(edgeAEnleve.get(i+2).getFirst());
				listAajouter.add(pairAajouter);
				//solutionTMP.getArcs().add(pairAajouter);
				//System.out.println(pairAajouter);
			}
			
			for (Iterator iterator = listAajouter.iterator(); iterator
					.hasNext();) {
				Paire<Integer, Integer> paire = (Paire<Integer, Integer>) iterator
						.next();
				if(isExist(solutionTMP.getArcs(),paire)){
					return solution;
				}
			}
			
			solutionTMP.getArcs().addAll(listAajouter);
			solutionTMP.getArcs().removeAll(edgeAEnleve);
			
			//System.out.println("Nbr d'arc après la recherche "+solutionTMP.getArcs().size());
			return solutionTMP;
		} else {
			// 2-OPT

			Integer f1 = edgeAEnleve.get(0).getFirst();
			Integer f2 = edgeAEnleve.get(1).getFirst();
			Integer s1 = edgeAEnleve.get(0).getSecond();
			Integer s2 = edgeAEnleve.get(1).getSecond();
			solutionTMP.getArcs().removeAll(edgeAEnleve);
			solutionTMP.getArcs().add(new Paire<Integer, Integer>(f1, f2));
			solutionTMP.getArcs().add(new Paire<Integer, Integer>(s1, s2));
			return solutionTMP;
		}
	}

	public ArrayList<Paire<Integer, Integer>> shaking(int k) {
		ArrayList<Paire<Integer, Integer>> edgeAEnleve = new ArrayList<Paire<Integer, Integer>>();
		Random rand = new Random();
		int alea = 0, aleaprec = 0;
		int pres = -1;
		for (int i = 0; i < k; i++) {

			alea = rand.nextInt(solution.getArcs().size()-1);
			//alea = rand.nextInt(graphEntree.getNbrVertex());
			while (alea == aleaprec) {
				//alea = rand.nextInt(graphEntree.getNbrVertex());
				alea = rand.nextInt(solution.getArcs().size()-1);

			}
			while (solution.getArcs().get(alea).getFirst().equals(pres)) {
				//alea = rand.nextInt(graphEntree.getNbrVertex());
				alea = rand.nextInt(solution.getArcs().size()-1);

			}
			while(edgeAEnleve.contains(solution.getArcs().get(alea))){
				//alea = rand.nextInt(graphEntree.getNbrVertex());
				alea = rand.nextInt(solution.getArcs().size()-1);

			}
			while(isExist(edgeAEnleve,
					solution.getArcs().get(alea))){
				//alea = rand.nextInt(graphEntree.getNbrVertex());
				alea = rand.nextInt(solution.getArcs().size()-1);

			}
			
			pres = solution.getArcs().get(alea).getSecond();
			edgeAEnleve.add(solution.getArcs().get(alea));
			aleaprec = alea;
		}

		return edgeAEnleve;
	}

	public boolean isExist(ArrayList<Paire<Integer, Integer>> interdit,Paire<Integer, Integer> pair) {
		for (Iterator iterator = interdit.iterator(); iterator.hasNext();) {
			Paire<Integer, Integer> paire1 = (Paire<Integer, Integer>) iterator
					.next();
			if ((paire1.getFirst().equals(pair.getFirst()))
					|| (paire1.getSecond().equals(pair.getFirst()))
					|| (paire1.getFirst().equals(pair.getSecond()))
					|| (paire1.getSecond().equals(pair.getSecond()))	) {
				return true;
			}
		}
		return false;
	}
	
	private static Solution rechercheMeilleurSolution(Solution solution,
			ArrayList<Paire<Integer, Integer>> interdit) {
		Integer deb;
		deb = (int) (Math.random() * solution.getGraphEntree().getNbrVertex());
		// Tirage au hazard
		interdit.add(getRandomPair(interdit, solution.getGraphEntree(), deb));
		interdit.add(getRandomPair(interdit, solution.getGraphEntree(),
				interdit.get(0).getSecond()));
		Paire<Integer, Integer> arcAInterdir;
		for (int i = 0; i < solution.getGraphEntree().getNbrVertex(); i++) {
			arcAInterdir = solution.getArcSortant(i);
			if (!arcAInterdir.equals(null)) {
				interdit.add(arcAInterdir);
			} else {
				continue;
			}
		}
		solution = permut_2_OPT(solution, interdit);

		return solution;
	}

	private static Solution permut_2_OPT(Solution solutionTMP,
			ArrayList<Paire<Integer, Integer>> interdit) {

		solutionTMP.getArcs().remove(interdit.get(0));
		solutionTMP.getArcs().remove(interdit.get(1));
		//System.out.println("Arc Intedit " + interdit.get(0));
		Integer tmp = solutionTMP.getArcSortant(interdit.get(1).getSecond())
				.getSecond();
		solutionTMP.getArcs().remove(
				solutionTMP.getArcSortant(interdit.get(1).getSecond()));
		solutionTMP.getArcs().add(
				new Paire<Integer, Integer>(interdit.get(0).getFirst(),
						interdit.get(1).getSecond()));
		solutionTMP.getArcs().add(
				new Paire<Integer, Integer>(interdit.get(1).getSecond(),
						interdit.get(1).getFirst()));
		solutionTMP.getArcs().add(
				new Paire<Integer, Integer>(interdit.get(1).getFirst(), tmp));

		return solutionTMP;
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
			while (interdit
					.contains(new Paire<Integer, Integer>(depart, arrive))) {
				depart = (int) (Math.random() * sol.getNbrVertex());
				arrive = (int) (Math.random() * sol.getNbrVertex());
				while (depart.equals(arrive)) {
					arrive = (int) (Math.random() * sol.getNbrVertex());
				}
			}
			interdit.add(new Paire<Integer, Integer>(depart, arrive));
		}
		// Elemination des cas inutile
		for (int i = 0; i < sol.getNbrVertex(); i++) {
			for (int j = 0; j < sol.getNbrVertex(); j++) {
				if (sol.isEdgeExist(i, j)
						&& !interdit
								.contains(new Paire<Integer, Integer>(i, j))) {
					for (int c = 0; c < sol.getNbrVertex(); c++) {

						// if a->b existe => intedit tout(c)->b
						if (!sol.isEdgeExist(c, i)) {
							interdit.add(new Paire<Integer, Integer>(c, i));
						}
						if (!sol.isEdgeExist(j, c)) {
							if (c != i) {
								// if a->b existe => intedit tout(c)->b
								interdit.add(new Paire<Integer, Integer>(j, c));
							}
						}
					}
				}
			}

			return null;
		}
		return sol;
	}
}
