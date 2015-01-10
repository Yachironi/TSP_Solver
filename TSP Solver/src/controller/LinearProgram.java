package controller;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import model.Graph;
import model.GraphGenerator;

public class LinearProgram {

	public IloCplex cplex=new IloCplex();
	//IloIntExpr obj;
	public IloLinearNumExpr obj;
	public IloNumVar[][] var;
	//IloIntVar[][] var;

	public int nbrVar;
	public double optimalSol;
	public double[][] solution_Final;

	public LinearProgram(Graph g) throws IloException {
		// Création d'un solveur CPLEX
		//cplex = new IloCplex();
		// Intialisation de la fonction Objectif
		obj = cplex.linearNumExpr();
		nbrVar = g.getNbrVertex();
		// Initialisation des variable CPLEX
		var = new IloIntVar[nbrVar][];
		for (int i = 0; i < nbrVar; i++) {
			var[i] = cplex.boolVarArray(nbrVar);
		}
		// Ajout de la fonction Objectif

		for (int i = 0; i < nbrVar; i++) {
			for (int j = 0; j < nbrVar; j++) {
				// if (i != j) {
				obj.addTerm(g.getEdgeCost(i, j), var[i][j]);
				// }
			}
		}
		cplex.addMinimize(obj);

		// Ajout de la premier contrainte sum(x_ij)=1
		for (int i = 0; i < nbrVar; i++) {
			IloLinearNumExpr expr = cplex.linearNumExpr();
			for (int j = 0; j < nbrVar; j++) {
				if (j != i) {
					expr.addTerm(1.0, var[i][j]);
				}
			}
			cplex.addEq(expr, 1.0);
		}

		// Ajout de la deuxième contrainte sum(x_ji)=1
		for (int j = 0; j < nbrVar; j++) {
			IloLinearNumExpr expr = cplex.linearNumExpr();
			for (int i = 0; i < nbrVar; i++) {
				if (i != j) {
					expr.addTerm(1.0, var[i][j]);
				}
			}
			cplex.addEq(expr, 1.0);
		}

	}

	public static boolean subtourExist(
			HashMap<Integer, LinkedList<Integer>> lst, Integer x) {
		if (lst.size() > 0) {
			for (HashMap.Entry<Integer, LinkedList<Integer>> entry : lst
					.entrySet()) {
				Integer key = entry.getKey();
				LinkedList<Integer> value = entry.getValue();
				if (value.contains(x)) {
					return true;
				}
			}
		}
		return false;
	}

	public static HashMap<Integer, LinkedList<Integer>> getSubToursList(
			Graph g, int x) throws IOException {
		HashMap<Integer, LinkedList<Integer>> lst = new HashMap<>();
		int key = 0;
		for (int i = 0; i < g.getNbrVertex(); i++) {

			if (!subtourExist(lst, i)) {
				LinkedList<Integer> subTour = new LinkedList<>();
				subTour.add(i);
				Integer tmp;
				if (g.getNext(i) != null)
					tmp = g.getNext(i);
				else
					break;
				// System.out.println(tmp==null);
				while ((g.getNext(tmp) != null) && (!(subTour.get(0) == tmp))
						&& (!subTour.contains(tmp))
						&& (!(subTour.get(subTour.size() - 1) == tmp))) {
					subTour.add(tmp);
					if (g.getNext(tmp) != null)
						tmp = g.getNext(tmp);
					else
						break;
				}
				if (subTour.get(0) == tmp) {
					subTour.add(tmp);
				}

				if (!(subTour.size() == (g.getNbrVertex() + 1))) {
					lst.put(key++, subTour);
				}
			}
		}
		return lst;
	}

	public Graph solve() throws IOException {
		Graph g=null;
		try {

			// solve model

			cplex.setOut(null);
			cplex.setWarning(null);
			
			//-------
			
			cplex.setParam(IloCplex.DoubleParam.CutsFactor, 1);
			cplex.setParam(IloCplex.IntParam.PreslvNd, -1);
			cplex.setParam(IloCplex.IntParam.RelaxPreInd, 0);
			cplex.setParam(IloCplex.LongParam.CutPass, -1);
			
			//-------
//			 cplex.setParam(IloCplex.DoubleParam.TreLim, 0.001);
		//cplex.setParam(IloCplex.DoubleParam.TiLim, 4);
//			 cplex.setParam(IloCplex.IntParam.IntSolLim,1);
			 //cplex.setParam(IloCplex.IntParam.ItLim,1);
//			 cplex.setParam(IloCplex.IntParam.AggCutLim,2);
			//cplex.setParam(IloCplex.IntParam.TimeLimit, 0.3);
	 //cplex.setParam(IloCplex.IntParam.NodeLim, 2);
			//cplex.setParam(IloCplex.IntParam.Threads, 6);
//			cplex.setParam(IloCplex.IntParam.AdvInd, 0);
			/*cplex.setParam(IloCplex.IntParam.RootAlg,
					IloCplex.Algorithm.Barrier);*/
			/*cplex.setParam(IloCplex.IntParam.BarCrossAlg,
					IloCplex.Algorithm.None);*/
			cplex.setParam(IloCplex.IntParam.Reduce, 0);
			cplex.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal);
			cplex.setParam(IloCplex.BooleanParam.PreInd, false);
			cplex.setParam(IloCplex.IntParam.MIPSearch,
					IloCplex.MIPSearch.Traditional);
			cplex.setParam(IloCplex.IntParam.MIPEmphasis, 4);
			boolean valide = false;
			double[][] solution = null;
			int iteration = 1;
			// Debut de boucle de résolution

			while (!valide) {

				System.out.println("Iteration N=" + (iteration++));
				solution = new double[nbrVar][nbrVar];
				
				if (cplex.solve()) {
					System.out.println("Fin appel CPLEX N°=" + iteration);
					System.out
							.println("Status de CPLEX : " + cplex.getStatus());
					for (int i = 0; i < nbrVar; i++) {
						double[] sol = cplex.getValues(var[i]);
						solution[i] = sol;
						solution[i][i] = 0;
					}
				}

				// valide = valide(solution);

				g = GraphGenerator.getRandomGraphSol(solution);
				// g.printGraph();
				HashMap<Integer, LinkedList<Integer>> subTourList = new HashMap<Integer, LinkedList<Integer>>();
				subTourList = getSubToursList(g, 0);
				valide = subTourList.size() == 0;
				System.out.println("Sous Résultat = " + cplex.getObjValue());
				if (!valide) {
					System.out.println("Sous Tour(s) detectée(s) !!!");

					System.out.println("Nombres des contraintes de soutours ajoutés : "
							+ subTourList.size());
					System.out.println();
//					System.out.println("Liste des sous Tours : ");
//					StringBuilder sb = new StringBuilder();
//					sb.append(subTourList.toString());
//					System.out.println(sb);
					// Generation des contraintes relatives aux sous tours
					// trouvé

					for (HashMap.Entry<Integer, LinkedList<Integer>> entry : subTourList
							.entrySet()) {
						Integer key = entry.getKey();
						LinkedList<Integer> subTour = entry.getValue();
						IloLinearNumExpr expr = cplex.linearNumExpr();
						StringBuilder sbs = new StringBuilder();
						for (int i = 0; i < subTour.size() - 1; i++) {

							expr.addTerm(1,
									var[subTour.get(i)][subTour.get(i + 1)]);
							sbs.append("X(" + subTour.get(i) + ","
									+ subTour.get(i + 1) + ")");
							sbs.append("+");
						}
						sbs.append("<=" + (subTour.size() - 2));
						// System.out.println(sbs);
						cplex.addLe(expr, subTour.size() - 2);
					}
				}
				String ch = cplex.getStatus().toString();
//				System.out.println(ch.equals("Feasible"));
//				System.out.println(subTourList.size());
				 //valide = subTourList.size() == 0;
				valide = subTourList.size() == 0&&(!ch.equals("Feasible"));

				subTourList = null;

			}
			System.out.println("Solution Trouvé");
			solution_Final=new double[nbrVar][nbrVar];
			for (int i = 0; i < nbrVar; i++) {
				double[] sol = cplex.getValues(var[i]);
				for (int j = 0; j < nbrVar; j++) {
					if (sol[j] == 1) {
						System.out.println("X[" + i + "," + j + "]");
					}
					solution_Final[i][j]=sol[j];
				}
			}
			GraphGenerator gr = new GraphGenerator();
			g = gr.getRandomGraph(solution_Final);
			optimalSol=cplex.getObjValue();
			System.out.println("La solution optimal est : "
					+ cplex.getObjValue());
			System.out.println("Status = " + cplex.getStatus());


			// end
			cplex.end();
		} catch (IloException e) {
			// System.err.println("Concert exception '" + e + "' caught");
			e.printStackTrace();
		}
		return g;
	}

	/*---------------     SOLV X^3       ------------------------*/
	public void solve2() throws IOException {

		try {

			// solve model

			//cplex.setOut(null);
			cplex.setWarning(null);
			// cplex.setParam(IloCplex.DoubleParam.TreLim, 0.1);
			// cplex.setParam(IloCplex.DoubleParam.TiLim, 100);
			// cplex.setParam(IloCplex.IntParam.IntSolLim,5);
			// cplex.setParam(IloCplex.IntParam.ItLim,1);
			// cplex.setParam(IloCplex.IntParam.AggCutLim,2);
			cplex.setParam(IloCplex.IntParam.TimeLimit, 0.1);
			// cplex.setParam(IloCplex.IntParam.NodeLim, 2);
			// cplex.setParam (IloCplex.IntParam.RootAlg,
			// IloCplex.Algorithm.Barrier);
			// cplex.setParam (IloCplex.IntParam.BarCrossAlg,
			// IloCplex.Algorithm.None);
			//
			cplex.setParam(IloCplex.IntParam.Reduce, 0);
			cplex.setParam(IloCplex.IntParam.RootAlg, IloCplex.Algorithm.Primal);
			cplex.setParam(IloCplex.BooleanParam.PreInd, false);
			// cplex.setParam(IloCplex.IntParam.Threads, 6);
			cplex.setParam(IloCplex.IntParam.MIPSearch,
					IloCplex.MIPSearch.Traditional);
			cplex.setParam(IloCplex.IntParam.MIPEmphasis, 4);
			boolean valide = false;
			double[][] solution = null;
			int iteration = 1;
			// Debut de boucle de résolution
			IloNumVar[] u = cplex.numVarArray(nbrVar, 0, Double.MAX_VALUE);
			while (!valide) {

				System.out.println("Iteration" + (iteration++));
				solution = new double[nbrVar][nbrVar];

				if (cplex.solve()) {
					System.out.println("Fin appel CPLEX N°=" + iteration);
					System.out
							.println("Status de CPLEX : " + cplex.getStatus());
					for (int i = 0; i < nbrVar; i++) {
						double[] sol = cplex.getValues(var[i]);
						solution[i] = sol;
						solution[i][i] = 0;
					}
				}

				// valide = valide(solution);

				Graph g = GraphGenerator.getRandomGraphSol(solution);
				// g.printGraph();
				HashMap<Integer, LinkedList<Integer>> subTourList = new HashMap<Integer, LinkedList<Integer>>();
				subTourList = getSubToursList(g, 0);
				valide = subTourList.size() == 0;
				System.out.println("Sous Résultat = " + cplex.getObjValue());
				if (!valide) {
					System.out.println("Sous Tour(s) detectée(s) !!!");

					System.out.println("Nombres des soutours : "
							+ subTourList.size());
					System.out.println("Liste des sous Tours : ");
					StringBuilder sb = new StringBuilder();
					sb.append(subTourList.toString());
					System.out.println(sb);
					// Generation des contraintes relatives aux sous tours
					// trouvé

					for (HashMap.Entry<Integer, LinkedList<Integer>> entry : subTourList
							.entrySet()) {
						Integer key = entry.getKey();
						LinkedList<Integer> subTour = entry.getValue();
						IloLinearNumExpr expr = cplex.linearNumExpr();
						StringBuilder sbs = new StringBuilder();
						for (int i = 0; i < subTour.size() - 1; i++) {
							expr.addTerm(1.0, u[subTour.get(i)]);
							expr.addTerm(-1.0, u[subTour.get(i + 1)]);
							expr.addTerm(subTour.size() - 1,
									var[subTour.get(i)][subTour.get(i + 1)]);
						}
						cplex.addLe(expr, subTour.size() - 2);
					}
				}
				String ch = cplex.getStatus().toString();
				System.out.println(ch.equals("Feasible"));
				System.out.println(subTourList.size());
				// valide = subTourList.size() == 0;
				//valide = subTourList.size() == 0&&(!ch.equals("Feasible"));

				subTourList = null;

			}
			System.out.println("Solution Trouvé");
			for (int i = 0; i < nbrVar; i++) {
				double[] sol = cplex.getValues(var[i]);
				for (int j = 0; j < nbrVar; j++) {
					if (sol[j] == 1) {
						System.out.println("X[" + i + "," + j + "]");
					}
				}
			}
			System.out.println("La solution optimal est : "
					+ cplex.getBestObjValue());
			System.out.println("Status = " + cplex.getStatus());

			Graph gres = GraphGenerator.getRandomGraph(solution);
			System.out.println(gres.getNumEdges());

			// end
			cplex.end();
		} catch (IloException e) {
			// System.err.println("Concert exception '" + e + "' caught");
			e.printStackTrace();
		}
	}

	/*------------------ Fin SOLV   ---------------- */
	public boolean valide(double[][] sol) {

		Graph gres = GraphGenerator.getRandomGraph(sol);
		if (gres.getNumEdges() < nbrVar)
			return false;
		for (int i = 0; i < nbrVar; i++) {
			for (int j = 0; j < nbrVar; j++) {
				if (sol[i][j] == 1 && sol[j][i] == 1)
					return false;

			}
		}
		return true;
	}

	public static void main(String[] args) throws IloException, IOException {
		// TODO Stub de la méthode généré automatiquement
		
		Parser p = new Parser("u1060.xml");
		System.out.println("Chargement du fichier XML...");
		Graph g = p.parse();
		System.out.println("Fin de chargement du fichier");
		System.out.println("Nombre de ville = "+g.getNbrVertex());
		LinearProgram lp = new LinearProgram(g);
		//g.printGraph();
		long begin = System.currentTimeMillis();
		Graph graph_solution = lp.solve();
		long end = System.currentTimeMillis();
		float millis = (float)(end - begin)/1000;
		int sec = (int)millis;
		float totalSecs=millis,hours,minutes,seconds;

		System.out.println("Temps d'execution = " + millis+" Secondes");
		
	} 


}
