package compress;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Compressor {
	HashMap <Character, Integer> charFreq = new HashMap<>();
	TreeMap <Integer, Character> tset;
	HashMap <Character, String> codeMap = new HashMap<>();
	PriorityQueue<Branch<Character>> pqueue; 
	
	File file;
	FileReader fr;
	

	
	public Compressor() {
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("The file you searched doesn't exist");
			e.printStackTrace();
		}
		
		makeTree();
		String code = "";
		genCode(code, pqueue.getFirst());
		System.out.println(codeMap);
		
		try {
			encode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("please enter the name of the file you want to compress.");
		String filename = sc.next();
		file = new File(filename);
        fr = new FileReader(file);
        sc.close();
        	
        int content;
            
        while ((content = fr.read()) != -1) {
            	
	       	char character = (char)content;
	            	
	       	if(charFreq.get(character)==null)
	       		
	       		charFreq.put(character, 1);
	       	
	      	else
	      		
	       		charFreq.put(character, charFreq.get(character)+1);
	            	
	        System.out.print(character);
                
        }
        
        fr.close();
        
        
        System.out.println();
        
        	tset = new TreeMap<>();
        	
        	for(Character c : charFreq.keySet())
        		tset.put(charFreq.get(c), c);
        	

        	System.out.println(tset);
        	
	}
	
	public void makeTree() {
		
		pqueue = new PriorityQueue<Branch<Character>>();
		
		for(Character c : charFreq.keySet()) {
			pqueue.add(new Branch<Character>(c), charFreq.get(c));
		}
		
		System.out.println(pqueue.toString());
		
		while(pqueue.size()>1) {
			
			int a = pqueue.getFirstPriority();
			Branch<Character> aa = pqueue.pop();
			
			int b = pqueue.getFirstPriority();
			Branch<Character> bb = pqueue.pop();
			
			int combinedP = a+b;
			
			pqueue.add(new Branch<Character>(aa,bb), combinedP);
		}
		
		System.out.println(pqueue);
		
		
	}
	
	
	public void genCode(String code, Branch<Character> current) {
		if(current.isLeaf)
			codeMap.put(current.info, code);
		else {
			//left
			genCode(code+"0", current.left);
			//right
			genCode(code+"1", current.right);
		}
		
	}
	
	public void encode() throws IOException {
		
		try {
			
			BufferedBitWriter bbw = new BufferedBitWriter("encodedTextFile");
		
			fr = new FileReader(file);
	    	
	        int content;
	        String contentCode;
	            
	        while ((content = fr.read()) != -1) {
	        	
	        	contentCode = codeMap.get((char)content);
	        	
	        	for(Character c : contentCode.toCharArray()) {
	        		if(c=='0')
	        			bbw.writeBit(true);
	        		else
	        			bbw.writeBit(false);
	        	}
	                
	        }
	        
	        fr.close();
	        bbw.close();
		
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		//Generate key-code file
		
		BufferedWriter bfw= new BufferedWriter(new FileWriter(new File("CodeKey")));
		for(Character c : codeMap.keySet()) {
			bfw.write(c);
			bfw.newLine();
			bfw.write(codeMap.get(c));
			bfw.newLine();
		}
		
		bfw.close();
		
	}
	
	//Priority Queue Class
	//To String Method
		//public String toString()
	//Add method
		//add(E e , int priority)
	//Pop method (first)
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Priority Queue, List with least common character in the front
		//Use binary tree to keep track letters'
		new Compressor();
		
	}

}
