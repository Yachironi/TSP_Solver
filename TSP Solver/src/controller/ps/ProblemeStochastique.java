package controller.ps;

import ilog.concert.IloException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import model.GraphStochastique;
import model.Solution;
import controller.Glouton;
import controller.Parser;

public class ProblemeStochastique {

	GraphStochastique graphEntree;
	Double solution;
	Integer nbrScenarios;
	ArrayList<Double> solutionK_TSP;
	ArrayList<GraphStochastique> scenarios;
	long startTime;
	long stopTime;

	public Integer getNbrScenarios() {
		return nbrScenarios;
	}

	public void setNbrScenarios(Integer nbrScenarios) {
		this.nbrScenarios = nbrScenarios;
	}

	public ProblemeStochastique(GraphStochastique graphEntree,
			Integer nbrScenarios) {
		super();
		solution = 0.0;
		solutionK_TSP = new ArrayList<Double>();
		this.graphEntree = graphEntree;
		this.nbrScenarios = nbrScenarios;
	}

	public Integer[][] generateRondomVertex(int nbrAretesStochastique) {
		Integer[][] listeVertex = new Integer[nbrAretesStochastique][2];
		ArrayList<Integer> depart = new ArrayList<Integer>();
		ArrayList<Integer> arrive = new ArrayList<Integer>();
		int deb, fin;
		Random random = new Random();
		for (int i = 0; i < nbrAretesStochastique; i++) {
			deb = random.nextInt(graphEntree.getNbrVertex());
			fin = random.nextInt(graphEntree.getNbrVertex());
			while (((depart.indexOf(deb) == arrive.indexOf(fin)) && ((depart
					.indexOf(deb) != -1) && (arrive.indexOf(fin) != -1)))
					|| (deb == fin)) {
				deb = random.nextInt(graphEntree.getNbrVertex());
				fin = random.nextInt(graphEntree.getNbrVertex());
			}
			depart.add(deb);
			arrive.add(fin);
		}

		for (int i = 0; i < nbrAretesStochastique; i++) {
			listeVertex[i][0] = depart.get(i);
			listeVertex[i][1] = arrive.get(i);
		}
		return listeVertex;
	}

	public Double getValeurDeuxiemeNiveau(Double cost,
			ArrayList<Double> coupAretesStochastique) {
		// double lower = cost - 3 * ecartType(coupAretesStochastique);
		double ecartType = ecartType(coupAretesStochastique);
		double lower = cost - 3 * ecartType;
		if (lower < 0) {
			lower = 0;
		}
		double upper = cost + 3 * ecartType;
		cost = (Math.random() * (upper - lower)) + lower;
		return cost;
	}

	private Double ecartType(ArrayList<Double> coupAretesStochastique) {

		Double variance = 0.0, moyenne = 0.0, ecartType = 0.0, somme = 0.0;

		for (Iterator iterator = coupAretesStochastique.iterator(); iterator
				.hasNext();) {
			Double double1 = (Double) iterator.next();
			somme = somme + double1; // Soucie
		}
		for (Iterator iterator = coupAretesStochastique.iterator(); iterator
				.hasNext();) {
			Double double1 = (Double) iterator.next();
			variance += Math.pow(double1 - moyenne, 2);
		}
		variance = variance / coupAretesStochastique.size();
		ecartType = Math.sqrt(variance);
		return ecartType;
	}

	/**
	 * Génération des différentes scénariots du problème
	 * 
	 * @param pourcentage
	 *            pourcentage des aretes determeniste
	 * @param nbr
	 *            nombre de scénario à génrée
	 * @return les différents scénarios
	 * @throws IOException
	 */
	public ArrayList<GraphStochastique> generationScenarios(double pourcentage,
			int nbr) {
		ArrayList<GraphStochastique> scenariosBruite = new ArrayList<GraphStochastique>();
		GraphStochastique graphTmp = new GraphStochastique(graphEntree);
		int nbrAretesDeterministe = (int) ((graphEntree.getNumEdges() * pourcentage) / 100);
		int nbrAretesStochastique = graphEntree.getNumEdges()
				- nbrAretesDeterministe;
		for (int i = 0; i < nbr; i++) {
			graphTmp = new GraphStochastique(graphEntree);
			Integer[][] tab = generateRondomVertex(nbrAretesStochastique);
			ArrayList<Double> coupAretesStochastique = new ArrayList<>();
			for (int j = 0; j < tab.length; j++) {
				coupAretesStochastique.add(graphEntree.getEdgeCost(tab[j][0],
						tab[j][1]));
			}
			for (int j = 0; j < nbrAretesStochastique; j++) {
				double cost = graphEntree.getEdgeCost(tab[j][0], tab[j][1]);
				cost = getValeurDeuxiemeNiveau(cost, coupAretesStochastique);
				graphTmp.setEdgeCost(tab[j][0], tab[j][1], cost);
				graphTmp.setEdgeType(tab[j][0], tab[j][1], true);
			}
			scenariosBruite.add(graphTmp);
			graphTmp = null;
		}
		return scenariosBruite;
	}

	public GraphStochastique getGraphEntree() {
		return graphEntree;
	}

	public void setGraphEntree(GraphStochastique graphEntree) {
		this.graphEntree = graphEntree;
	}

	public Double getSolution() {
		return solution;
	}

	public void setSolution(Double solution) {
		this.solution = solution;
	}

	public ArrayList<Double> getSolutionK_TSP() {
		return solutionK_TSP;
	}

	public void setSolutionK_TSP(ArrayList<Double> solutionK_TSP) {
		this.solutionK_TSP = solutionK_TSP;
	}

	public void startTimer() {
		startTime = System.currentTimeMillis();
	}

	public void stopTimer() {
		stopTime = System.currentTimeMillis();
	}

	public float getExcutionTime() {
		return (float) (startTime - stopTime) / 1000;
	}

	/**
	 * Resolution du TSP Stochastique
	 * 
	 * @return le graphe solution
	 */
	public Solution solve(double pourcentageDeteministe, int nbrScenarios,
			int kMax, int itMax) {
		Solution resultat = new Solution();
		startTimer();
		scenarios = generationScenarios(pourcentageDeteministe, nbrScenarios);
		//GraphStochastique scenarioTMP = new GraphStochastique(scenarios.get(0));
		// GraphStochastique solutionScenarioTMP = VNS.solve(scenarioTMP);
		GraphStochastique scenarioTMP =null;
		for(int i=0;i<nbrScenarios;i++){
		scenarioTMP = new GraphStochastique(scenarios.get(i));
		VNS vns = new VNS(2, 5, scenarioTMP);
		resultat = vns.solve(itMax, kMax);
		System.out.println("Scenario N° = "+i);
		System.out.println("Taille Graph Solution ="+resultat.getArcs().size());
		System.out.println("Graph Solution ="+resultat);
		System.out.println("Cout Total = " + resultat.getTotalCost(scenarioTMP));
		System.out.println("Glouton  = "+Solution.graphToSolution(Glouton.solve(scenarios.get(i))).getTotalCost(scenarios.get(i))); 
		}
		stopTimer();
		return resultat;
	}

	public static void main(String[] args) throws IloException, IOException {
		// TODO Stub de la méthode généré automatiquement

		Parser p = new Parser("gr17.xml");
		System.out.println("Chargement du fichier XML...");
		GraphStochastique g = new GraphStochastique(p.parse());
		System.out.println("Fin de chargement du fichier");
		System.out.println("Nombre de ville = " + g.getNbrVertex());
		ProblemeStochastique ps = new ProblemeStochastique(g, 5);
		// g.printGraph();
		long begin = System.currentTimeMillis();
		// Graph graph_solution = ps.solve();
		System.out.println("Graph Initial");
		System.out.println("================ DEB ================");
		g.printGraph();
		System.out.println("================ FIN =================");
		Solution resultat = ps.solve(10, 100, 5, 5);
	//	System.out.println("Cout Total = " + resultat.getTotalCost(g));

		// System.out.println("=========== Senario Genere ===========");
		// ArrayList<GraphStochastique> gen = ps.generationScenarios(80, 5);
		// for (int i = 0; i < ps.getNbrScenarios(); i++) {
		// System.out.println("=========== Senario "+i +"  ===========");
		// gen.get(i).printGraph();
		// }
		long end = System.currentTimeMillis();
		float millis = (float) (end - begin) / 1000;
		int sec = (int) millis;
		float totalSecs = millis, hours, minutes, seconds;
		System.out.println("Temps d'execution = " + millis + " Secondes");

	}
}
