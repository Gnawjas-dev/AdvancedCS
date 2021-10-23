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
	File binaryfile = new File("BinaryFile");
	
	HashMap <String, String> codeKey = new HashMap<>();
	
	public DeCompressor() throws IOException{
		readCodeKeys();
		decodeWrite();
	}
	
	public void decodeWrite() throws IOException {
		BufferedBitReader bbr = new BufferedBitReader("encodedTextFile");
        
		FileWriter fr = new FileWriter(new File("decompressed"));
		String content = "";
//		int temp;
//		while((temp = br.read())!=-1) {
//			content+=(char)temp;
//			if(codeKey.containsKey(content)) {
//				fr.write(codeKey.get(content));
//				content="";
//			}
//		}
        
        while (bbr.hasNext()) {
        	if(bbr.readBit())
    			content+="0";
    		else
    			content+="1";
        	for(String s : codeKey.keySet()) {
        		if(content.equals(s)) {
        			fr.write(codeKey.get(s));
        			content="";
        		}
        	}
        	
        	//use mr.friedman's file to convert to binary
        	//then make method to BufferedWriter War and Peace on new text file
        }
        
        fr.close();
        bbr.close();
	}
	
	public void readCodeKeys() throws IOException {
		FileReader reader = new FileReader(new File("CodeKey"));
		int content;
		String line = "";
		String charac = "";
		int code;
		
		while((content=reader.read())!=-1) {
			charac+= (char) content;
			reader.read();
			while((code = reader.read())!=-1 && (char)code!='\n') {
				line+=(char)code;
			}
			codeKey.put(line, charac);
			charac=""; line="";
			
		}
		
		System.out.println(codeKey);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new DeCompressor();
	}

}
