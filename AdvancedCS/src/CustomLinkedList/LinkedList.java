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
	
	public void add(E info) {
		if(first==null) {
			first = new Node(info);
			return;
		} else {
			Node current  = first;
			while(current.next!=null) {
				current=current.next;
			}
			current.next = new Node(info);
		}
	}
	
	public int size() {
		int count=0;
		if(first==null)
			return 0;
		Node current = first;
		while(current.next != null) {
			current=current.next;
			count++;
		}
		return count+1;
	}
	
	public E get(int index) {
		try {
			Node current = first;
			for(int j=0; j<index; j++) {
				current = current.next;
			}
			return current.data;
			
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public void clear() {
		first=null;
	}
	
	public E remove(int index) {
		
		try {
			Node current = first;
			for(int j=0; j<index-1; j++) {
				current = current.next;
			}
			
			E temp = current.next.data;
			current.next=current.next.next;
			
			return temp;
			
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public E remove() {
		Node current = first;
		while(current.next!=null) {
			current=current.next;
		}
		E temp = current.data;
		current=null;
		return temp;
	}
	
	public static void main(String[] args) {
		LinkedList<String> myList = new LinkedList<>();
		myList.add("hi", 0);
		myList.add("bye", 0);
		myList.add("I am Jason", 0);
		System.out.println(myList.get(1));
		myList.remove(1);
		System.out.println(myList.get(1));
		System.out.println(myList.size());
	}
	
	
}
