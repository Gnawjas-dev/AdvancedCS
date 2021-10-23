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
	//instance variables - character frequency map, [unused] treemap, code to character map, create priority queue in compressor
	HashMap <Character, Integer> charFreq = new HashMap<>();
//	TreeMap <Integer, Character> tset;
	HashMap <Character, String> codeMap = new HashMap<>();
	PriorityQueue<Branch<Character>> pqueue; 
	
	//reuse fr, dont instantiate file because I use scanner to find file (catches IOException and says file not found)
	File file;
	FileReader fr;
	

//	running all compressor procedures here (command central?)
	public Compressor() {
		
		//try catch block for exceptions
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		
		makeTree();
		String code = "";
		genCode(code, pqueue.getFirst());
//		System.out.println(codeMap);
		
		try {
			encode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Your file has been encoded in \"encodedTextFile\"");
	}
	
	public void read() throws IOException {
//		Mock file chooser, can use JFileChooser and other objects
		Scanner sc = new Scanner(System.in);
		System.out.println("please enter the name of the file you want to compress.");
		String filename = sc.next();
		file = new File(filename);
		
//		close scanner + read file
        fr = new FileReader(file);
        sc.close();
        	
        int content;

//		while loop as the file size isn't determined
//		save the char val. while reading, cast to char
//		if not already in map, put in with 1 freq
//		otherwise put in with x+1 freq, where x is the previous amount for said char
        
        while ((content = fr.read()) != -1) { //-1 if character is null or DNE
            	
	       	char character = (char)content;
	            	
	       	if(charFreq.get(character)==null)
	       		
	       		charFreq.put(character, 1);
	       	
	      	else
	      		
	       		charFreq.put(character, charFreq.get(character)+1);
//			if it prints out the text file, probably ran without crashing and ran correctly
//	        System.out.print(character);
                
        }
        
        fr.close();
        
        
//        System.out.println();
//      unused tree set method
//
//        	tset = new TreeMap<>();
//        	
//        	for(Character c : charFreq.keySet())
//        		tset.put(charFreq.get(c), c);
//        	
//			checking if treemap works or no
//        	System.out.println(tset);
        	
	}

//	the priority queue way
	public void makeTree() {
		
		pqueue = new PriorityQueue<Branch<Character>>();
		
//		add to pqueue the individual characters + frequencies
//		sort least frequent to top		
		for(Character c : charFreq.keySet()) {
			pqueue.add(new Branch<Character>(c), charFreq.get(c));
		}
		
//		System.out.println(pqueue.toString());
		
//		making the non-leaf branches / extending upward
//		save the combined priority and create a "parent"
//		branch with the two children + their summed 
//		priorities
		
		while(pqueue.size()>1) {
			
			int a = pqueue.getFirstPriority();
			Branch<Character> aa = pqueue.pop();
			
			int b = pqueue.getFirstPriority();
			Branch<Character> bb = pqueue.pop();
			
			int combinedP = a+b;
			
			pqueue.add(new Branch<Character>(aa,bb), combinedP);
		}
		
//		System.out.println(pqueue);
		
		
	}
	
	
	public void genCode(String code, Branch<Character> current) {
//		starts at the top and will completely end if all leafs has been reached
//		if not it will run itself over and over progressing through the tree until
//		leaf was reached (then one loop stops so the other loops also reach leaves)		
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
//		using Mr. Friedman's BBW and compressing it down to weird ascii characters
		try {
			
			BufferedBitWriter bbw = new BufferedBitWriter("encodedTextFile");

			// read original text file again
			fr = new FileReader(file);
	    	
	        int content;
	        String contentCode;
	            
	        while ((content = fr.read()) != -1) {
	        	
	        	//for each character get their code and turn into ascii chars
	        	contentCode = codeMap.get((char)content);
	        	
	        	for(Character c : contentCode.toCharArray()) { //using toCharArray because it works and I look cool
	        		if(c=='0')
	        			bbw.writeBit(true);
	        		else
	        			bbw.writeBit(false);
	        	}
	                
	        }
	        //close everything like a good kid
	        fr.close();
	        bbw.close();
		
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		//Generate key-code file
		//only a couple more lines to go!
		
		BufferedWriter bfw= new BufferedWriter(new FileWriter(new File("CodeKey")));
		for(Character c : codeMap.keySet()) {
			bfw.write(c);
			bfw.newLine();
			bfw.write(codeMap.get(c));
			bfw.newLine();
			//don't worry about "extra-lines" for line-separator character, will be solved in decompressor
		}
		
		//close!!
		bfw.close();
		
	}
	
	//some in class notes about PQ
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
		
		//runs the constructor as command central
		new Compressor();
		
	}

}
