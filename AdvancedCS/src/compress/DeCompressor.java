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
	
	//only need the encoded Text file  + codekey to work
	File file = new File("encodedTextFile");
	
	
	HashMap <String, String> codeKey = new HashMap<>();
	
	public DeCompressor() throws IOException{
		//same compressor constructor runner format
		readCodeKeys();
		decodeWrite();
	}
	
	public void decodeWrite() throws IOException {
		BufferedBitReader bbr = new BufferedBitReader("encodedTextFile");
        
		FileWriter fr = new FileWriter(new File("decompressed"));
		String content = "";
		
//		revised because errors occurred (somehow worked for 1 test case)		
//		int temp;
//		while((temp = br.read())!=-1) {
//			content+=(char)temp;
//			if(codeKey.containsKey(content)) {
//				fr.write(codeKey.get(content));
//				content="";
//			}
//		}
        
		
//		use buffered bit reader and convert back into codes and immediately convert 
//		from codemap to character to write into the decompressed file
        while (bbr.hasNext()) {
        	if(bbr.readBit()) //depended on the true/false settings in the compressor (it's the same)
    			content+="0";
    		else
    			content+="1";
        	for(String s : codeKey.keySet()) {
        		if(content.equals(s)) {
        			fr.write(codeKey.get(s));
        			content="";
        		}
        	}
        	
        	//self-written homework ignore please
        	//use mr.friedman's file to convert to binary
        	//then make method to BufferedWriter War and Peace on new text file
        }
        
        //close everything like good kid I am
        fr.close();
        bbr.close();
	}
	
	public void readCodeKeys() throws IOException {
		//instantiating
		FileReader reader = new FileReader(new File("CodeKey"));
		int content;
		String line = "";
		String charac = "";
		int code;
		
		//first read letter char, read again for next line
		//then read char by char, build string until code finished (check for line-separator char and null)
		//reverse codekey (compared to compressor's codekey, we need to build characters using the keyed codes)
		while((content=reader.read())!=-1) {
			charac+= (char) content;
			reader.read();
			while((code = reader.read())!=-1 && (char)code!='\n') {
				line+=(char)code;
			}
			codeKey.put(line, charac);
			charac=""; line="";
			
		}
		
//		System.out.println(codeKey);
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new DeCompressor();
	}

}
