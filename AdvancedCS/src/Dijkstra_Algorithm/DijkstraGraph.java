package Dijkstra_Algorithm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import Dijkstra_Algorithm.DijkstraGraph.Vertex;

//same dijkstra graph as others' but read, draw, and save methods are different

public class DijkstraGraph {
	
	public class Edge {
		Vertex v1, v2;
		int info;
		boolean path = false;
		private Edge (Vertex v1, Vertex v2) {
			this.v1=v1;
			this.v2=v2;
			info=distance(v1.x, v1.y, v2.x, v2.y);
		}
		
		public String toString() {
			return v1.toString() + " is connected to " + v2.toString() + " by " + info;
		}
		
		private Vertex getOtherVertex(Vertex v) {
			if(v.equals(v1)) return v2;
			else if (v.equals(v2)) return v1;
			else return null;
		}
	}
	
	public class Vertex{
		
		String info;
		int x; 
		int y;
		boolean hover = false;
		boolean path = false;
		HashSet<Edge> neighbors;
		private Vertex(String info, int x, int y) {
			this.info=info;
			this.x=x; this.y=y;
			neighbors = new HashSet<Edge>();
		}
		private void connect(Edge e) {
			neighbors.add(e);
		}
		public String toString() {
			return (String) info;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
	}
	
	private HashMap<String, Vertex> graph;
	
	public HashMap<String, Vertex> getGraph() {
		return graph;
	}
	
	public DijkstraGraph() {
		graph=new HashMap<String, Vertex>();
	}
	
	public void add(String info, int x, int y) {
		if(!contains(info))
			graph.put(info, new Vertex(info, x, y));
	}
	
	public int size() {
		return graph.size();
	}
	
	public String toString() {
		String yes = "";
		for(String info : graph.keySet()) {
			yes+="[" + info + ": ";
			for(Edge e : getVertex(info).neighbors) {
				yes+=e.toString() + ", ";
			}
			yes+="] ";
		}
		return yes;
	}
	
	public void connect(String one, String two) {
		
		Edge temp = new Edge(getVertex(one), getVertex(two)); 
		getVertex(one).connect(temp);
		getVertex(two).connect(temp);
		
	}
	
	public boolean contains(String info) {
		return graph.containsKey(info);
	}
	
	private Vertex getVertex(String info) {
		return graph.get(info);
	}
	
	public String dijkstra(String start, String target){
		PQ<Vertex> toVisit = new PQ<>();
		HashMap<Vertex, Integer> distances = new HashMap<>();
		for(String e : graph.keySet()) {
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
			System.out.println(toVisit);
			visited.add(curr);
			if(curr.equals(getVertex(target))) {
				return backtrace(leadsTo, start, target);
			}
			for(Edge e : curr.neighbors) {
				Vertex neighbor = e.getOtherVertex(curr);
				if(visited.contains(neighbor)) {
					continue;
				}
				int dist = e.info + distances.get(curr);
				if(dist < distances.get(neighbor)) {
					distances.put(neighbor, dist);
					toVisit.put(neighbor, dist);
					leadsTo.put(neighbor, curr);
				}
			}
		}
		return "Not Found";
		
		
	}

	public String backtrace(HashMap<Vertex, Vertex> nextprev, String start, String target) {
		
		//convert path into list of traversable connections (reversing order in a sense)
		ArrayList<Vertex> path = new ArrayList<>();
		HashMap<Vertex, Vertex> map = nextprev;
		Vertex curr = getVertex(target);
		
		while(curr!=null) {
			
			path.add(0, curr);
			curr = map.get(curr);
			
		}
		
		//while you do return a string answer, this program focus more on turning the path booleans true for 
		//both the point and edges in the dijkstra method
		String answer = "";
		for(int i=0; i<path.size(); i++) {
			answer+=path.get(i).info;
			path.get(i).path=true;
			if(i!=path.size()-1) {
				answer+=" -> ";
			}
		}
		
		for(Vertex v : graph.values()) {
			for(Edge e : v.neighbors) {
				if(e.getOtherVertex(v).path&&v.path) {
					e.path=true;
				}
			}
		}
		
		return answer;
		
	}
	
	public int distance(int x1, int y1, int x2, int y2) {
		//for convenience
		return (int)Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2));
	}
	
	//save function!!
	public void save() throws IOException {
		//writing into save file
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("save.txt")));
		//print size so read method knows how many times to read
		int vertices = graph.size();
		pw.println(vertices);
		
		//save each point's info
		for(Vertex v : graph.values()) {
			//name of point + x + y
			pw.println(v.info + " " + v.getX() + " " + v.getY());
		}
		
		//then save each neighbor's name after the point's name
		for(Vertex v : graph.values()) {
			//name of point + x + y
			pw.print(v.info);
			for(Edge e : v.neighbors) {
				pw.print(" "+e.getOtherVertex(v).info);
			}
			pw.println();
		}
		
		//means have to loop twice in read
		
		pw.close();
	}
	
	public void read() throws IOException {
		
		//reading from save.txt
		BufferedReader br = new BufferedReader(new FileReader("save.txt"));
		//read the size and running 2 forloops of size-length
		int size = Integer.parseInt(br.readLine());
		
		for(int i=0; i<size; i++) {
			//name of point + x + y = [name] + [x] + [y] = [0] + [1] + [2]
			String[] s = br.readLine().split(" ");
			add(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
		}
		for(int i=0; i<size; i++) {
			//point neighbors = [point] + [neighbors] = [0] + [1] + [2] + [3] + ...
			String[] s = br.readLine().split(" ");
			//loops through the array of point neighbor (while skipping the first one) to connect all neighbors to the point
			for(int j=1; j<s.length; j++) {
				connect(s[0], s[j]);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		gps = new DijkstraGraph<String>();
//		gps.add("A");
//		gps.add("C");
//		gps.connect("A", "C", 3);
//		gps.connect("A", "D", 2);
//		gps.connect("A", "B", 5);
//		gps.connect("B", "H", 12);
//		gps.connect("D", "E", 4);
//		gps.connect("E", "F", 5);
//		gps.connect("E", "G", 1);
//		gps.connect("F", "G", 6);
//		gps.connect("F", "H", 4);
//		System.out.println(gps.dijkstra("A", "H"));
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		for(Vertex v : graph.values()) {
			int x = v.getX();
			int y = v.getY();
			//drawing hovered points first (least relevant)
			if(v.hover) {
				g.setColor(Color.RED);
			} 
			//drawing dijkstra'd points
			else if (v.path) {
				g.setColor(Color.BLUE);
			}
			//drawing neutral points (unaffected)
			else {
				g.setColor(Color.BLACK);
			}
			//draw the circle with the selected color w/ center being the click
			g.fillOval(x-10, y-10, 20, 20);
			
			//drawing the lines in connection
			//loops through neighbors
			for(Edge e : v.neighbors) {
				
				//saves neighbor's x,y
				int x2 = e.getOtherVertex(v).x;
				int y2 = e.getOtherVertex(v).y;
				
				//if dijkstra'd then blue, otherwise just black
				if(e.path) g.setColor(Color.BLUE); 
				else g.setColor(Color.BLACK);
				
				//make the line a little thicker to see better
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(5));
				
				//draw a line from the point to its neighbor
				g.drawLine(x, y, x2, y2);
			}
		}
	}

	public void checkHover(int mousex, int mousey) {
		// TODO Auto-generated method stub
		for(Vertex v : graph.values()) {
			//ten is a radius for the circles I'm drawing but I didnt make it a variable name
			//if the mouse is within a rectangular bound of the vertex then turn that square's hover on
			if(v.getX()-10<mousex && v.getX()+10>mousex && v.getY()+10>mousey && v.getY()-10<mousey) {
				v.hover = true;
			}
			//else then turn it false
			//only 1 vertex should be hovering
			else v.hover = false;
		}
	}
}
