package compress;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DeCompressor {
	File file = new File("encodedTextFile");
	HashMap <String, String> codeKey = new HashMap<>();
	
	public DeCompressor() throws IOException{
		readCodeKeys();
	}
	
	public void decodeWrite() throws IOException {
		FileReader fr = new FileReader(file);
    	
        int content;
           
        
        while ((content = fr.read()) != -1) {
        	//use mr.friedman's file to convert to binary
        	//then make method to BufferedWriter War and Peace on new text file
        }
        
        fr.close();
	}
	
	public void readCodeKeys() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("CodeKey")));
		reader.readLine();
		String line;
		String secondLine;
		
		while((line=reader.readLine())!=null) {
			secondLine=reader.readLine();			
			codeKey.put(line, secondLine);
						//character, code
		}
		
		System.out.println(codeKey);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new DeCompressor();
	}

}
