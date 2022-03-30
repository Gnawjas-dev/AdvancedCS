package Dijkstra_Algorithm;

import java.util.HashMap;

import Graph.EdgeGraph;

public class Algorithm {
	
	public Algorithm() {
		EdgeGraph<String, Integer> gps = new EdgeGraph<>();
		PQ<String> toVisit = new PQ<String>();
		gps.connect("A", "C", 3);
		gps.connect("A", "D", 2);
		gps.connect("A", "B", 5);
		gps.connect("B", "H", 12);
		gps.connect("D", "E", 4);
		gps.connect("E", "F", 5);
		gps.connect("E", "G", 1);
		gps.connect("F", "G", 6);
		gps.connect("F", "H", 4);
		HashMap<String, Integer> distances = new HashMap<>();
	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Algorithm();
	}

}
