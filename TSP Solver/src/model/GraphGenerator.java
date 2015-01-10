package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

public class GraphGenerator {

	public static Graph getRandomGraph(int numVertex, int maxEdgeCost) {
		Graph g = new Graph(numVertex);
		int tmpCost;
		Random rnd = new Random();
		for (int i = 0; i < numVertex; i++) {
			for (int j = 0; j < numVertex; j++) {
				tmpCost = (int) (rnd.nextInt(maxEdgeCost) + 1);
				if (tmpCost > 0 && i != j)
					g.setEdgeCost(i, j, tmpCost);
				if(i==j){
					g.setEdgeCost(i, j, Double.MAX_VALUE);
				}
			}
		}
		return g;
	}
	
	public static Graph getRandomGraph(double[][] matrix) {
		Graph g = new Graph(matrix[0].length);
		
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
					g.setEdgeCost(i, j, matrix[i][j]);
			}
		}
		return g;
	}
	public static Graph getRandomGraphSol(double[][] matrix) {
		Graph g = new Graph(matrix[0].length);
		for (int i = 0; i < matrix[0].length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
					g.setEdgeCost(i, j, matrix[i][j]);
					if(i==j){
						g.setEdgeCost(i, j, 0);
					}
			}
		}
		return g;
	}

	public static Graph read(BufferedReader r) throws IOException {
		// return new Graph(20);
		String line;

		String[] edge = new String[3];
		int i, j;
		double edgeCost;
		int numVertex;
		numVertex = Integer.parseInt(r.readLine());
		Graph g = new Graph(numVertex);

		while (true) {

			line = r.readLine();
			if (line == null)
				break;
			edge = line.split(" ", 3);
			i = Integer.parseInt(edge[0]);
			j = Integer.parseInt(edge[1]);
			edgeCost = Double.parseDouble(edge[2]);
			g.setEdgeCost(i, j, edgeCost);

		}

		return g;

	}

}
