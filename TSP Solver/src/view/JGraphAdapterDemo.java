/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* ----------------------
 * JGraphAdapterDemo.java
 * ----------------------
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   -
 *
 * $Id$
 *
 * Changes
 * -------
 * 03-Aug-2003 : Initial revision (BN);
 * 07-Nov-2003 : Adaptation to JGraph 3.0 (BN);
 *
 */
package view;

import ilog.concert.IloException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;

import javax.swing.JApplet;

import model.Graph;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

import com.mxgraph.view.mxGraph;

import controller.LinearProgram;
import controller.Parser;

// resolve ambiguity

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 * 
 * @author Barak Naveh
 * @since Aug 3, 2003
 */
public class JGraphAdapterDemo extends JApplet {

	private static final long serialVersionUID = 3256444702936019250L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	//
	private JGraphModelAdapter<String, DefaultEdge> jgAdapter;

	/**
	 * {@inheritDoc}
	 * @throws IloException 
	 * @throws IOException 
	 */
	public void init(String xmlFile) throws IloException, IOException {
		// create a JGraphT graph
		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedMultigraph<String, DefaultEdge>(
				DefaultEdge.class);

		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);

		JGraph jgraph = new JGraph(jgAdapter);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);

		Parser p = new Parser(xmlFile);	
		Graph graph_parsed = p.parse();
		LinearProgram lp = new LinearProgram(graph_parsed);
		Graph graph_solved = lp.solve();
		/*
		 * String v1 = "v1"; String v2 = "v2"; String v3 = "v3"; String v4 =
		 * "v4";
		 * 
		 * // add some sample data (graph manipulated via JGraphT)
		 * g.addVertex(v1); g.addVertex(v2); g.addVertex(v3); g.addVertex(v4);
		 * 
		 * /* g.addEdge(v1, v2); g.addEdge(v2, v3); g.addEdge(v3, v1);
		 * g.addEdge(v4, v3);
		 */

		// position vertices nicely within JGraph component
		for (int i = 0; i < graph_parsed.getNbrVertex(); i++) {

			Random rand = new Random();
			int x = ((rand.nextInt(width - 150)) * i) % 1400;
			int y = ((rand.nextInt(height - 180)) * i) % 500;
			System.out.println("Width"+width);
			System.out.println("height"+height);
			System.out.println(x+","+y);
			positionVertexAt(i, x, y);
			
			// break;
			/*
			 * positionVertexAt(v2, 60, 200); positionVertexAt(v3, 310, 230);
			 * positionVertexAt(v4, 380, 70);
			 */
		}

		// that's all there is to it!...
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);

		Color c = DEFAULT_BG_COLOR;
		String colorStr = null;

		try {
			colorStr = getParameter("bgcolor");
		} catch (Exception e) {
		}

		if (colorStr != null) {
			c = Color.decode(colorStr);
		}

		jg.setBackground(c);
	}

	@SuppressWarnings("unchecked")
	private void positionVertexAt(Object vertex, int x, int y) {
		
		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
		AttributeMap attr =  cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		Rectangle2D newBounds = new Rectangle2D.Double(x, y,
				bounds.getWidth() - 30, bounds.getHeight() - 8);

		GraphConstants.setBounds(attr, newBounds);

		// TODO: Clean up generics once JGraph goes generic
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	/**
	 * a listenable directed multigraph that allows loops and parallel edges.
	 */
	private static class ListenableDirectedMultigraph<V, E> extends
			DefaultListenableGraph<V, E> implements DirectedGraph<V, E> {
		private static final long serialVersionUID = 1L;

		ListenableDirectedMultigraph(Class<E> edgeClass) {
			super(new DirectedMultigraph<V, E>(edgeClass));
		}
	}
}

// End JGraphAdapterDemo.java
