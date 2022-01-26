package Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class KevinBacon {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		EdgeGraph<String, String> graph = read("actors.txt", "movies.txt", "movie-actors.txt");
		System.out.println(graph.bfs("Patrick Jordan", "Zoe Saldana"));
	}

	
	public static EdgeGraph<String, String> read(String file1, String file2, String file3) throws IOException {
		
		EdgeGraph<String, String>temp=new EdgeGraph<String, String>();
		BufferedReader br=new BufferedReader(new FileReader(file1));
		
		HashMap<String, String> idname = new HashMap<>();
		HashMap<String, String> idmovie = new HashMap<>();
		ArrayList<String[]> movieactors = new ArrayList<>();
		
		String line;
		while((line = br.readLine()) != null) {
			String[] load = line.split("~");
			idname.put(load[0], load[1]);
		}
		
		br=new BufferedReader(new FileReader(file2));
		
		while((line = br.readLine()) != null) {
			String[] load = line.split("~");
			idmovie.put(load[0], load[1]);
		}
		
		br=new BufferedReader(new FileReader(file3));
		
		
		while((line = br.readLine()) != null) {
			movieactors.add(line.split("~"));
		}
		
		int count = 0;
		for(int i=0; i<movieactors.size()-1; i++) {	
			if(movieactors.get(i+1)[0].equals(movieactors.get(i)[0])){
				for(int j=i+1; j<movieactors.size(); j++) {
					if(movieactors.get(j)[0].equals(movieactors.get(i)[0])) {
						temp.connect(idname.get(movieactors.get(i)[1]), idname.get(movieactors.get(j)[1]), idmovie.get(movieactors.get(i)[0]));
						count++;
					}
					else break;
				}
			}
		}
		
		System.out.println(count);
		
		return temp;
		
	}
}
