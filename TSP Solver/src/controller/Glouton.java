package controller;

import model.GraphStochastique;

public class Glouton {

	public static GraphStochastique glouton(GraphStochastique g) {
		
		// if(g==null)
		// return null;
		//
		// Vertex depart = g.getVilles().get((int) Math.random() *
		// g.getNbVilles());
		// ArrayList<PaireVertex> cheminInterdit = new ArrayList<PaireVertex>();
		// Vertex vilaInterdit = depart;
		// for (int i = 0; i < g.getNbVilles()-1; i++) {
		// PaireVertex tmp = null;
		// if(i==0) {
		// tmp = procheVoisin(depart, g.getCouts(), cheminInterdit,null);
		// }
		// else{
		// tmp = procheVoisin(depart, g.getCouts(), cheminInterdit,
		// vilaInterdit);
		// }
		//
		// cheminInterdit.add(tmp);
		// depart = tmp.getSecond();
		// }
		// Vertex sortante =
		// cheminInterdit.get(cheminInterdit.size()-1).getSecond();
		// Vertex entrante = cheminInterdit.get(0).getFirst();
		//
		// cheminInterdit.add(new PaireVertex(sortante,entrante));
		// System.out.println(cheminInterdit);
		//
		// Graph graphe = new Graph(g.getVilles());
		// LinkedHashMap<PaireVertex, Double> map = new
		// LinkedHashMap<PaireVertex, Double>();
		// for (PaireVertex p : cheminInterdit) {
		// map.put(p, g.getCouts().get(p).doubleValue());
		// }
		// graphe.setCouts(map);

		return null;
	}

}
