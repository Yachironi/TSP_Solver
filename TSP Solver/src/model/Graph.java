package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Graph {
	public static final int MAXCOST = 999999;
	private int nbrVertex;
	private Vector<Vector<Double>> edgeCost;

	public Vector<Vector<Double>> getEdgeCost() {
		return edgeCost;
	}

	public void setEdgeCost(Vector<Vector<Double>> edgeCost) {
		this.edgeCost = edgeCost;
	}

	public static int getMaxcost() {
		return MAXCOST;
	}

	public void setNbrVertex(int numVertex) {
		this.nbrVertex = numVertex;
	}

	   @Override
	    protected Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }
	   public Graph(Graph g) {

			this.nbrVertex = g.getNbrVertex();
			edgeCost = new Vector<Vector<Double>>(nbrVertex);
			for (int i = 0; i < nbrVertex; i++) {
				Vector<Double> costVector = new Vector<Double>(nbrVertex);
				for (int j = 0; j < nbrVertex; j++)
					costVector.add(new Double(g.getEdgeCost(i, j)));
				edgeCost.add(costVector);
			}
			
		}
	public Graph(int numVertex, Vector<Vector<Double>> edgeCost) {
		super();
		this.nbrVertex = numVertex;
		this.edgeCost = edgeCost;
	}

	public Graph(int numVertex) {

		this.nbrVertex = numVertex;

		edgeCost = new Vector<Vector<Double>>(numVertex);

		for (int i = 0; i < numVertex; i++) {
			Vector<Double> costVector = new Vector<Double>(numVertex);
			for (int j = 0; j < numVertex; j++)
				costVector.add(new Double(Graph.MAXCOST));
			edgeCost.add(costVector);
		}

	}

	public int getNbrVertex() {
		return nbrVertex;
	}

	// Print Graph as adjacency matrix by default
	public void printGraph() throws IOException {
		BufferedWriter w = new BufferedWriter(new PrintWriter(System.out));
		printGraph(w);
	}

	public void printGraph(BufferedWriter w) throws IOException {

		double tmpCost;
		w.write("Graph Represented By Adjacency Matrix:");
		w.write("\nnumVertex: " + getNbrVertex() + "   numEdges: "
				+ getNumEdges());
		w.write("\n      ");
		for (int i = 0; i < getNbrVertex(); i++)
			w.write(String.format("%5s ", i));
		w.write("\n");
		for (int i = 0; i < getNbrVertex(); i++) {
			w.write(String.format("%5s ", i));
			for (int j = 0; j < getNbrVertex(); j++) {
				tmpCost = getEdgeCost(i, j);
				if (tmpCost < Graph.MAXCOST)
					w.write(String.format("%5.0f ", tmpCost));
				else
					w.write(String.format("  INF "));
			}
			w.write("\n");
		}
		w.write("\n");
		w.flush();

	}

	public int getNumEdges() {
		int numEdges = 0;
		double tmpEdgeCost;
		Iterator<Vector<Double>> itx = edgeCost.iterator();
		while (itx.hasNext()) {
			Vector<Double> row = (Vector<Double>) itx.next();
			Iterator<Double> ity = row.iterator();
			while (ity.hasNext()) {
				tmpEdgeCost = (Double) ity.next();
				if (tmpEdgeCost > 0 && tmpEdgeCost < Graph.MAXCOST)
					numEdges++;
			}
		}
		return numEdges;
	}

	public double getEdgeCost(int i, int j) {
		return edgeCost.get(i).get(j);
	}

	public void setEdgeCost(int i, int j, double cost) {
		edgeCost.get(i).set(j, cost);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public Integer getNext(int x) {
		int i = 0;
		for (i = 0; i < nbrVertex; i++) {

			if (getEdgeCost(x, i) > 0 && getEdgeCost(x, i) != Double.MAX_VALUE) {
				return i;
			}

		}
		return null;
	}

	public List<Integer> adjacent(int s) {
		// TODO Stub de la méthode généré automatiquement
		List<Integer> adj = new ArrayList<Integer>();
		for (int i = 0; i < nbrVertex; i++) {
			if (edgeCost.get(s).get(i) > 0)
				adj.add(i);
		}
		System.out.println(adj);
		return adj;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		return null;
	}
}
