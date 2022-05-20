package Dijkstra_Algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

public class PQ <E>{
		
	private ArrayList <Node> priorityQueue= new ArrayList<Node>();
	
	private class Node {
		private E info;
		private int priority;
		
		public Node(E info, int priority) {
			this.info = info;
			this.priority = priority;
		}
		
		public int getPrior() {
			return this.priority;
		}
		
		public String toString() {
			return (String)info;
		}
		
	}
	
	public int getFirstPriority() {
		return priorityQueue.get(0).getPrior();
	}
	
	public E getFirst() {
		return priorityQueue.get(0).info;
	}
	
	public void add(E info, int priority) {
		priorityQueue.add(new Node(info, priority));
		priorityQueue.sort(Comparator.comparingInt(Node::getPrior));
//		ArrayList
		
	}
	
	public boolean contains(E info) {
		for(Node n : priorityQueue) {
			if(n.info.equals(info)) return true;
		}
		return false;
	}
	
	public void remove(E info) {
		for(int i=0; i<priorityQueue.size(); i++) {
			if(priorityQueue.get(i).info.equals(info)) {
				priorityQueue.remove(i);
				break;
			}
		}
	}
	
	public void put(E info, int priority) {
		if(contains(info)){
			remove(info);
		}
		add(info, priority);
	}
	
	public E pop(){
		E temp = priorityQueue.get(0).info;
		priorityQueue.remove(0);
		return temp;
	}
	
	public String toString() {
		String response="";
		for(Node n : priorityQueue) {
			response+="["+n.info.toString()+", "+n.priority+"] ";
		}
		return response;
	}
	
	public int size() {
		return priorityQueue.size();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PQ<String> pq = new PQ<>();
		pq.put("Cool", 1);
		pq.put("fly", 3);
		pq.put("Cool", 5);
		pq.put("fly", 7);
		pq.put("flyy", 6);
		pq.put("flyy", 1);
		System.out.println(pq);
		System.out.println(pq.pop());
	}

}
