package compress;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class Compressor {
	HashMap <Character, Integer> charFreq = new HashMap<>();
	TreeMap <Integer, Character> freqChar;
	
	public Compressor() {
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException {
		
		File file = new File("UncompressedFile");
		 
        FileReader fr = new FileReader(file);
        	
        int content;
            
        while ((content = fr.read()) != -1) {
            	
	       	char character = (char)content;
	            	
	       	if(charFreq.get(character)==null)
	       		
	       		charFreq.put(character, 1);
	       	
	      	else
	      		
	       		charFreq.put(character, charFreq.get(character)+1);
	            	
	        System.out.print(character);
                
    
            
        }
        
        	freqChar = new TreeMap<>();
        	
        	for(Character c : charFreq.keySet())
        		freqChar.put(charFreq.get(c), c);
        	

        	System.out.println(freqChar);
        	
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
