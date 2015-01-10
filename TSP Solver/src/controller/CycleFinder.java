package controller;

import java.util.Stack;

import model.Graph;

public class CycleFinder {

	private boolean[] visited;
	private int[] edgeTo;
	private Stack cycle;
	private boolean[] onCurrentStack;

	public CycleFinder(Graph graph) {
		visited = new boolean[graph.getNbrVertex()];
		onCurrentStack = new boolean[graph.getNbrVertex()];
		edgeTo = new int[graph.getNbrVertex()];
		for (int v = 0; v < graph.getNbrVertex(); v++) {
			if (!visited[v]) {
				dfs(graph, v);
			}
		}
	}

	private void dfs(Graph graph, int s) {
		visited[s] = true;
		onCurrentStack[s] = true;
		for (int w : graph.adjacent(s)) {
			if (containsCycle()) {
				return;
			} else if (!visited[w]) {
				edgeTo[w] = s;
				dfs(graph, w);
			} else if (onCurrentStack[w]) {
				determineEntireCyclePath(s, w);
			}
		}
		onCurrentStack[s] = false;
	}

	public boolean containsCycle() {
		return null != cycle;
	}

	private void determineEntireCyclePath(int s, int w) {
		cycle = new Stack();
		for (int x = s; x != w; x = edgeTo[x]) {
			cycle.push(x);
		}
		cycle.push(w);
		cycle.push(s);
		//System.out.println(cycle.toString());
	}
}