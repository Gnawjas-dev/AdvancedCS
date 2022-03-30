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
	//edge class with to string to get movie name and getOtherVertex as getter method
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
	
	//vertex class with connect to connect to a edge
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
	
	//the graph variable that everything runs on
	//not sure if this is E, Vertex or actually E,T
	private HashMap<E, Vertex> graph;
	
	//instantiating the graph
	public EdgeGraph() {
		graph=new HashMap<E, Vertex>();
	}
	
	//use to add vertices to graph, but is called in connect method so
	public void add(E info) {
		if(!contains(info))
			graph.put(info, new Vertex(info));
	}
	
	//returns graph size
	public int size() {
		return graph.size();
	}
	
	//be able to print out the graph to check for accuracy
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
	
	//calls add func for easier use
	public void connect(E one, E two, T info) {
		
		if(!contains(one)) add(one);
		if(!contains(two)) add(two);
		
		Edge temp = new Edge(getVertex(one), getVertex(two), info); 
		getVertex(one).connect(temp);
		getVertex(two).connect(temp);
		
	}
	
	//some helper methods for complex functions
	public boolean contains(E info) {
		return graph.containsKey(info);
	}
	
	private Vertex getVertex(E info) {
		return graph.get(info);
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//	}

	//I am returning an arraylist because it is an ordered list of connections
	
	public ArrayList<String> bfs(E start, E target) {
		
		HashMap<Vertex, Vertex> nextprev = new HashMap<>();
		Vertex curr = getVertex(start);
		ArrayList<Vertex> toVisit = new ArrayList<>();
		nextprev.put(curr, null);
		toVisit.add(curr);
		
		//returns this instead of the usual answer if crash
		ArrayList<String> error = new ArrayList<>();
		error.add("actors not found");
		
		//try catch for returning error list
		try {
			
			//add neighbors to ordered list of "toVisit", as in "breadth first search", this goes as wide as possible
			//ordered, always starting from the first/top of the toVisit list
			//keep a path
			while(toVisit.size()!=0) {			
				
				curr=toVisit.remove(0);
				
				for(Edge e : curr.neighbors) {
					
					Vertex v = e.getOtherVertex(curr);
					
					//if it reaches target, turn the path into a list of connected vertexes from start to target
					if(v.info.equals(target)) {
						nextprev.put(v, curr);
						ArrayList<Vertex> temp = backtrace(nextprev, start, target);
						
						ArrayList<String> actors = new ArrayList<>();
						for(Vertex ve : temp) {
							actors.add(ve.toString());
						}
						
						//Explains print below
						return print(actors);
						
					}
					
					if(!nextprev.containsKey(v)) {
						toVisit.add(v);
						nextprev.put(v, curr);
					}
					
				}
				
			}
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			return error;
		}
		
		return error;
		
		//if bfs didnt find anything also return error as well
	}
	
	
	public ArrayList<String> print(ArrayList<String> actors) {
		
		//basically this method turns all the connections and get the edge infos, whose toString() method mentions the 2 vertices it's connected to
		//so we are actually returning the list of edges and their toString() methods. 
		
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
		
		//convert path into list of traversable connections (reversing order in a sense)
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
	
	//not used for kevin bacon game but can run, saves into a file
	
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
	
	//without index this method loops the entire vertices and finds the vertex with the greatest amount of neighbors. 
	public String mostPopular() {
		int max = -1;
		String person = "";
		for(E e : graph.keySet()) {
			if(getVertex(e).neighbors.size()>max) {
				max=getVertex(e).neighbors.size();
				person=(String) getVertex(e).info;
			}
		}
		if(person==null||max==-1) {
			return "Person not found";
		}
		else return person+" is the most popular actor with a connection to "+ max + " people";
	}
	
	//finds the neighbor with the great amount of neighbors in the targets list
	public String mostPopular(E info) {
		int max = -1;
		String popular = "";
		Vertex person = getVertex(info);
		try {
			for(Edge e : person.neighbors) {
				if(e.getOtherVertex(person).neighbors.size()>max) {
					max=e.getOtherVertex(person).neighbors.size();
					popular=(String) e.getOtherVertex(person).info;
				}
			}
		} catch (Exception e) {
			return "person not found";
		}
		if(max==-1) {
			return "Person not found";
		}
		else return popular+" is the most popular neighbor of " + person + " with a connection to "+ max + " people";
	}
	
	//finds the person the target is connected the most times with, 
	//I didn't have time to implement a way to return everyone who had the highest amount of connections
	
	public String bestFriend(E info) {
		Vertex person = getVertex(info);
		int max = -1;
		String bestfriend = "";
		try {
			for(Edge e : person.neighbors) {
				Vertex target = e.getOtherVertex(person);
				int count = 0;
				for(Edge e1 : target.neighbors) {
					if(e1.getOtherVertex(target).info.equals(info)) {
						count++;
					}
				}
				if(count>max) {
					bestfriend = (String) target.info;
					max = count;
				}
			}
			//rough catch to return error
		} catch (Exception e) {
			return "Person not found";
		}
		if(max==-1) {
			return "Person not found";
		}
		else return person + " is besties with " + bestfriend + " because they are connected by " + max + " movies";
	}

	//not rly a function because of its simplicity
	//chooses a vertex at random
	
	public String random() {
		int random = (int)Math.random()*graph.size();
		Vertex p1 = null;
		int i=0;
		for(E e : graph.keySet()) {
			p1=getVertex(e);
			if(i>random) break;
			i++;
		}
		
		if(p1==null) return "An error occurred"; //shouldn't happen but maybe so
		return (String)p1.info;
	}
	
	//makes a list of actors whose name contains the entered parameter
	
	public ArrayList<String> queryActors(E info){
		ArrayList<String> ret = new ArrayList<>();
		ArrayList<String> error = new ArrayList<>();
		error.add("Error has occurred, please enter in a legal input");
		for(E e : graph.keySet()) {
			if(e.toString().indexOf(info.toString())!=-1){
				ret.add(e.toString());
			}
		}
		return ret;
	}
	
}
