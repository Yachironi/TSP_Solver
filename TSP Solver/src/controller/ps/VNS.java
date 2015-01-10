package controller.ps;

import model.GraphStochastique;

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
