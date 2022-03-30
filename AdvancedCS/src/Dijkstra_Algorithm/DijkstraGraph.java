package Dijkstra_Algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;



public class DijkstraGraph <E, T> {
	static DijkstraGraph<String, Integer> gps;
	
	private class Edge {
		Vertex v1, v2;
		T info;
		private Edge (Vertex v1, Vertex v2, T info) {
			this.v1=v1;
			this.v2=v2;
			this.info=info;
		}
		
		public String toString() {
			return v1.toString() + " is connected to " + v2.toString() + " by " + (String)info;
		}
		
		private Vertex getOtherVertex(Vertex v) {
			if(v.equals(v1)) return v2;
			else if (v.equals(v2)) return v1;
			else return null;
		}
	}
	
	private class Vertex{
		
		E info;
		HashSet<Edge> neighbors;
		private Vertex(E info) {
			this.info=info;
			 neighbors = new HashSet<Edge>();
		}
		private void connect(Edge e) {
			neighbors.add(e);
		}
		public String toString() {
			return (String) info;
		}
		
	}
	
	private HashMap<E, Vertex> graph;
	
	public DijkstraGraph() {
		graph=new HashMap<E, Vertex>();
	}
	
	public void add(E info) {
		if(!contains(info))
			graph.put(info, new Vertex(info));
	}
	
	public int size() {
		return graph.size();
	}
	
	public String toString() {
		String yes = "";
		for(E info : graph.keySet()) {
			yes+="[" + info + ": ";
			for(Edge e : getVertex(info).neighbors) {
				yes+=e.toString() + ", ";
			}
			yes+="] ";
		}
		return yes;
	}
	
	public void connect(E one, E two, T info) {
		
		if(!contains(one)) add(one);
		if(!contains(two)) add(two);
		
		Edge temp = new Edge(getVertex(one), getVertex(two), info); 
		getVertex(one).connect(temp);
		getVertex(two).connect(temp);
		
	}
	
	public boolean contains(E info) {
		return graph.containsKey(info);
	}
	
	private Vertex getVertex(E info) {
		return graph.get(info);
	}
	
	public String dijkstra(E start, E target){
		PQ<Vertex> toVisit = new PQ<>();
		HashMap<Vertex, Integer> distances = new HashMap<>();
		for(E e : graph.keySet()) {
			distances.put(getVertex(e), Integer.MAX_VALUE);
		}
		HashMap<Vertex, Vertex> leadsTo = new HashMap<>();
		Vertex curr = getVertex(start);
		
		HashSet<Vertex> visited = new HashSet<>();
		
		leadsTo.put(curr, null);
		toVisit.put(curr, 0);
		distances.put(curr, 0);
		
		while(toVisit.size()!=0) {
			curr=toVisit.pop();
			visited.add(curr);
			if(curr.equals(getVertex(target))) {
				return backtrace(leadsTo, start, target);
			}
			for(Edge e : curr.neighbors) {
				Vertex neighbor = e.getOtherVertex(curr);
				if(visited.contains(neighbor)) {
					continue;
				}
				int dist = (int) e.info + distances.get(curr);
				if(dist < distances.get(neighbor)) {
					distances.put(neighbor, dist);
					toVisit.put(neighbor, dist);
					leadsTo.put(neighbor, curr);
				}
			}
		}
		return "Not Found";
		//returns A B H, priority queue not working OR it doesnt compare routes. 
		//I dont know why it goes to B instead of D (I tried using .reverse on my pqueue's comparator and I don't think it worked
		
	}

	public String backtrace(HashMap<Vertex, Vertex> nextprev, E start, E target) {
		
		//convert path into list of traversable connections (reversing order in a sense)
		ArrayList<Vertex> path = new ArrayList<>();
		HashMap<Vertex, Vertex> map = nextprev;
		Vertex curr = getVertex(target);
		
		while(curr!=null) {
			
			path.add(0, curr);
			curr = map.get(curr);
//			System.out.println(curr);
			
		}
		
		String answer = "";
		for(int i=0; i<path.size(); i++) {
			answer+=path.get(i).info;
			if(i!=path.size()-1) {
				answer+=" -> ";
			}
		}
		
		return answer;
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		gps = new DijkstraGraph<String, Integer>();
		gps.connect("A", "C", 3);
		gps.connect("A", "D", 2);
		gps.connect("A", "B", 5);
		gps.connect("B", "H", 12);
		gps.connect("D", "E", 4);
		gps.connect("E", "F", 5);
		gps.connect("E", "G", 1);
		gps.connect("F", "G", 6);
		gps.connect("F", "H", 4);
		System.out.println(gps.dijkstra("A", "H"));
	}
}
