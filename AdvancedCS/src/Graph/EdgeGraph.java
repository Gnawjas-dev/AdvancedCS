package Graph;

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



public class EdgeGraph <E, T> {

	private class Edge {
		Vertex v1, v2;
		T info;
		private Edge (Vertex v1, Vertex v2, T info) {
			this.v1=v1;
			this.v2=v2;
			this.info=info;
		}
		
		public String toString() {
			return "Connected to: " + v1.toString() + " and " + v2.toString() + " with a relationship of " + (String)info;
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
		private void remove(Vertex v) {
			neighbors.remove(v);
		}
		public String toString() {
			return (String) info;
		}
		
	}
	
	private HashMap<E, Vertex> graph;
	
	public EdgeGraph() {
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
	
//	public E remove(E info) {
//		for(Edge e : getVertex(info).neighbors) {
//			e.remove(getVertex(info));
//		}
//		graph.remove(info);
//		return info;
//		
//	}
	
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String> bfs(E start, E target) {
		
		HashMap<Vertex, Vertex> nextprev = new HashMap<>();
		Vertex curr = getVertex(start);
		ArrayList<Vertex> toVisit = new ArrayList<>();
		nextprev.put(curr, null);
		toVisit.add(curr);
		
		while(toVisit.size()!=0) {			
			
			curr=toVisit.remove(0);
			
			for(Edge e : curr.neighbors) {
				
				Vertex v = e.getOtherVertex(curr);
				
				if(v.info.equals(target)) {
					nextprev.put(v, curr);
					ArrayList<Vertex> temp = backtrace(nextprev, start, target);
					
					ArrayList<String> actors = new ArrayList<>();
					for(Vertex ve : temp) {
						actors.add(ve.toString());
					}
					
					return print(actors);
					
				}
				
				if(!nextprev.containsKey(v)) {
					toVisit.add(v);
					nextprev.put(v, curr);
				}
				
			}
			
		}
		
		return null;
		
	}
	
	
	public ArrayList<String> print(ArrayList<String> actors) {
		
		ArrayList<String>ans = new ArrayList<>();
		
		for(int i=1; i<actors.size(); i++) {
			String one = actors.get(i-1);
			String two = actors.get(i);
			String movie = "null";
			for(Edge e : getVertex((E) one).neighbors) {
				if(e.v1.equals(getVertex((E)one))&&e.v2.equals(getVertex((E)two))||e.v2.equals(getVertex((E)one))&&e.v1.equals(getVertex((E)two))) {
					movie = e.toString();
					ans.add(movie);
					break;
				}
			}
			
		}
		return ans;
	}

	public ArrayList<Vertex> backtrace(HashMap<Vertex, Vertex> nextprev, E start, E target) {
		
		ArrayList<Vertex> path = new ArrayList<>();
		HashMap<Vertex, Vertex> map = nextprev;
		Vertex curr = getVertex(target);
		
		while(curr!=null) {
			
			path.add(0, curr);
			curr = map.get(curr);
//			System.out.println(curr);
			
		}
		
		return path;
		
	}
	
	public void save() throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("graph.txt")));
		pw.println(graph.size());
		for(E e : graph.keySet()) {
			pw.println((String)e + " " + getVertex(e).neighbors.size());
			for(Edge edge : getVertex(e).neighbors) {
				pw.print(edge.getOtherVertex(getVertex(e)) + " ");
				pw.println((String)edge.info);
			}
		}
		pw.close();
	}
	


}
