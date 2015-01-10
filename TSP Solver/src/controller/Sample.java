package controller;

import java.io.IOException;

import model.Graph;
import model.GraphGenerator;

public class Sample {

	public static void main(String[] args) throws IOException {
		// TODO Stub de la méthode généré automatiquement
		System.out.println("Minimal Working Example");
		// GraphGenerator est une classe qui génere les graph automatiquement 
		GraphGenerator gg = new GraphGenerator();
		// Création d'un graphe avec des valeurs alèatoire 
		Graph g = gg.getRandomGraph(20, 10);
		// Affichage de graphe sous forme de tableau d'adjacence
	
		g.printGraph();
	}

}
