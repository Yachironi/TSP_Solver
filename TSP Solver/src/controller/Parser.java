package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Graph;
import model.GraphStochastique;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
	private String urlXML;

	public Parser(String urlXML) {

		this.urlXML = urlXML;
	}

	
//	
//	public void parse(LinkedHashMap<Double, ArrayList<Double>> map) {
//		try {
//
//			File fXmlFile = new File(urlXML);
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = null;
//			try {
//				doc = dBuilder.parse(fXmlFile);
//			} catch (FileNotFoundException e) {
//				// TODO: handle exception
//				System.err.println("Erreur fichier : " + urlXML
//						+ " introuvable");
//			}
//
//			if (doc != null) {
//				// optional, but recommended
//				// read this -
//				// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//				doc.getDocumentElement().normalize();
//
//				// sys.println("Root element :" +
//				// doc.getDocumentElement().getNodeName());
//
//				NodeList nListGraph = doc.getElementsByTagName("graph");
//
//				// sys.println("----------------------------");
//				Node nNode = nListGraph.item(0);
//
//				// sys.println("\nCurrent Element :"+ nNode.getNodeName());
//
//				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//
//					// Element eElement = (Element) nNode;
//					NodeList nListVertex = doc.getElementsByTagName("vertex");
//
//					/*
//					 * Boucle pour lire
//					 */
//					// ArrayList<Vertex> mesVilles = new ArrayList<Vertex>();
//					for (int temp = 0; temp < nListVertex.getLength(); temp++) {
//						Node nodeVertex = nListVertex.item(temp);
//						if (nodeVertex.getNodeType() == Node.ELEMENT_NODE) {
//							String numCity = String.valueOf(temp);
//							// mesVilles.add(new
//							// Vertex(Integer.parseInt(numCity)));
//
//							// sys.println("DEBUT ________ Ville numero " +
//							// numCity + "\n");
//
//							Element elementVertex = (Element) nodeVertex;
//							NodeList listEdge = elementVertex
//									.getElementsByTagName("edge");
//
//							/*
//							 * La je récupère la valeur du cost
//							 */
//							ArrayList<Double> values = new ArrayList<Double>();
//
//							for (int indice = 0, size = listEdge.getLength(); indice < size; indice++) {
//								if (indice == temp)
//									values.add(Double.MAX_VALUE);
//								// values.add(null);
//								values.add(Double.parseDouble(listEdge
//										.item(indice).getAttributes()
//										.getNamedItem("cost").getNodeValue()));
//
//								// //sys.println("aaa = "+Double.parseDouble(listEdge.item(indice).getAttributes().getNamedItem("cost").getNodeValue()));
//								// //sys.println("Cost = "+listEdge.item(indice).getAttributes().getNamedItem("cost").getNodeValue()
//								// + " to city "+
//								// listEdge.item(indice).getTextContent());
//							}
//							if (temp == nListVertex.getLength() - 1)
//								values.add(Double.MAX_VALUE);
//							// values.add(null);
//
//							// sys.println("num city " +numCity);
//							// sys.println(values);
//							map.put(new Double(Integer.parseInt(numCity)),
//									values);
//							// sys.println("\nFIN ________ Ville numero " +
//							// numCity + "\n");
//						}
//					}
//					// g.setCities(mesVilles);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	
	public Graph parse() {
		Graph g = null;
		try {

			File fXmlFile = new File(urlXML);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = null;
			try {
				doc = dBuilder.parse(fXmlFile);
			} catch (FileNotFoundException e) {
				// TODO: handle exception
				System.err.println("Erreur fichier : " + urlXML
						+ " introuvable");
			}

			if (doc != null) {

				doc.getDocumentElement().normalize();
				NodeList nListGraph = doc.getElementsByTagName("graph");

				Node nNode = nListGraph.item(0);

				// sys.println("\nCurrent Element :"+ nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					// Element eElement = (Element) nNode;
					NodeList nListVertex = doc.getElementsByTagName("vertex");

					/*
					 * Boucle pour lire
					 */
					// ArrayList<Vertex> mesVilles = new ArrayList<Vertex>();
					g = new Graph(nListVertex.getLength());
					for (int temp = 0; temp < nListVertex.getLength(); temp++) {
						Node nodeVertex = nListVertex.item(temp);
						if (nodeVertex.getNodeType() == Node.ELEMENT_NODE) {
							String numCity = String.valueOf(temp);
							Element elementVertex = (Element) nodeVertex;
							NodeList listEdge = elementVertex
									.getElementsByTagName("edge");

							ArrayList<Double> values = new ArrayList<Double>();

							int indice = 0, size = 0;
							for (indice = 0, size = listEdge.getLength(); indice < size; indice++) {
								Double cost = 0.0;

								cost = Double.parseDouble(listEdge.item(indice)
										.getAttributes().getNamedItem("cost")
										.getNodeValue());
								g.setEdgeCost(temp, Integer.parseInt(listEdge
										.item(indice).getTextContent()), cost);

							}
							g.setEdgeCost(temp, temp, Double.MAX_VALUE);
							if (temp == nListVertex.getLength() - 1)
								values.add(Double.MAX_VALUE);

						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}

	/*
	 * private static String getChildElementContent(Element e, String childName)
	 * { NodeList children = e.getElementsByTagName(childName);
	 * if(children.getLength() > 0) { return
	 * children.item(0).getAttributes().getNamedItem("cost").getNodeValue(); }
	 * return ""; }
	 */

	public static ArrayList<String> listDirectory(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		ArrayList<String> mesXml = new ArrayList<String>();
		if (files != null) {
			int cpt = 0;
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().contains(".xml")) {
					// sys.println("#" + (cpt + 1) + "   "
					// + files[i].getName().replace(".xml", ""));
					mesXml.add(files[i].getName());
					cpt++;
				}
			}
		}
		return mesXml;
	}

}
