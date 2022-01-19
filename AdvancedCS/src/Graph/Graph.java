package Graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Graph <E> {
	
	private class Vertex{
		
		E info;
		HashSet<Vertex> neighbors;
		private Vertex(E info) {
			this.info=info;
			 neighbors = new HashSet<Vertex>();
		}
		private void connect(Vertex v) {
			neighbors.add(v);
		}
		private void remove(Vertex v) {
			neighbors.remove(v);
		}
		public String toString() {
			return (String) info;
		}
		
	}
	
	private HashMap<E, Vertex> graph;
	
	public Graph() {
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
			for(Vertex v : getVertex(info).neighbors) {
				yes+=v.info + ", ";
			}
			yes+="] ";
		}
		return yes;
	}
	
	public E remove(E info) {
		for(Vertex v : getVertex(info).neighbors) {
			v.remove(getVertex(info));
		}
		graph.remove(info);
		return info;
		
	}
	
	public void connect(E one, E two) {
		
		if(!contains(one)) add(one);
		if(!contains(two)) add(two);
		
		getVertex(one).connect(getVertex(two));
		getVertex(two).connect(getVertex(one));
	}
	
	public boolean contains(E info) {
		return graph.containsKey(info);
	}
	
	private Vertex getVertex(E info) {
		return graph.get(info);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph <String> g = new Graph <String>();
		
		try {
			g=read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(g);
		System.out.println(g.bfs("gregory", "tiffany"));
		
	}

	public ArrayList<Vertex> bfs(E start, E target) {
		
		HashMap<Vertex, Vertex> nextprev = new HashMap<>();
		Vertex curr = getVertex(start);
		ArrayList<Vertex> toVisit = new ArrayList<>();
		nextprev.put(curr, null);
		
		while(curr!=null) {
			
			for(Vertex v : curr.neighbors) {
				
				if(v.info==target) {
					
					nextprev.put(v, curr);
					return backtrace(nextprev, start, target);
					
				}
				
				else if(!nextprev.containsKey(v)) {
					toVisit.add(v);
					nextprev.put(v, curr);
				}
				
				
			}
			
			curr=toVisit.remove(0);
			
		}
		
		return null;
		
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
			for(Vertex v : getVertex(e).neighbors) {
				pw.println((String)v.info);
			}
		}
		pw.close();
	}
	
	public static Graph<String> read() throws IOException {
		
		Graph<String>temp=new Graph<String>();
		BufferedReader br=new BufferedReader(new FileReader("graph.txt"));
		int graphsize = Integer.parseInt(br.readLine());
		for(int i=0; i<graphsize; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String name = st.nextToken();
			temp.add(name);
			int size = Integer.parseInt(st.nextToken());
			
			for(int j=0; j<size; j++) {
				temp.connect(name, br.readLine());
			}
		}
		return temp;
	}
}
