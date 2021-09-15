package CustomLinkedList;



public class LinkedList <E>{
	//generic class
	
	private class Node {
		
		Node next = null;
		E data;
		
		public Node (E data) {
			this.data = data;
		}
		
	}
	
	private Node first;
	
	public void add(E info, int index) {
		
		if(first==null) {
			first = new Node(info);
			return;
		}
		
		if(index==0) {
			Node newFirst = new Node(info);
			newFirst.next = first;
			first = newFirst;
			return;
		}
		
		try {
			Node current =first;
			for(int j=0; j<index; j++) {
				current = current.next;
			}
			
			Node newNode = new Node(info);
			newNode.next = current.next;
			current.next = newNode;
			
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
		
			
		
	}

}
