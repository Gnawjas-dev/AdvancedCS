package compress;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

public class PriorityQueue <E>{
		
	ArrayList <Node> priorityQueue;
	TreeSet <Node> pq = new TreeSet<>(new setSort());
	
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
		
	}
	
	public class setSort implements Comparator<Node> {

		@Override
		public int compare(Node one, Node two) {
			// TODO Auto-generated method stub
			if(one.getPrior()>two.getPrior())
				return 1;
			else if (one.getPrior()<two.getPrior())
				return -1;
			else
				return 0;
		}
		
		
	}
	
	public void add(E info, int priority) {
		priorityQueue.add(new Node(info, priority));
		priorityQueue.sort(Comparator.comparingInt(Node::getPrior));
//		ArrayList
		
	}
	
	
	public E pop(){
		E temp = priorityQueue.get(0).info;
		priorityQueue.remove(0);
		return temp;
	}
	
	public String toString() {
		String response="";
		for(Node n : priorityQueue) {
			response+="["+n.info.toString()+" "+String.valueOf(n.priority)+"] ";
		}
		return response;
	}
	
	public int size() {
		return priorityQueue.size();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
