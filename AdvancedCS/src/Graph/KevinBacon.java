package Graph;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class KevinBacon {
	//core graph variable that gets called by everything
	EdgeGraph<String, String> graph;
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new KevinBacon();
	}
	
	public KevinBacon() throws IOException {
		//read in and build graph
		graph = read("actors.txt", "movies.txt", "movie-actors.txt");
		
		//dimensions for GUI Components
		Dimension fr = new Dimension(1000,1000);
		Dimension hz = new Dimension(1000,200);
		Dimension scr = new Dimension(1000,800);
		Dimension button = new Dimension(150, 100);
		Dimension buttonlist = new Dimension(150,200);
		Dimension sch = new Dimension(200, 400);
		Dimension res = new Dimension(1000,800);
		Dimension labels = new Dimension(200,200);
		
		//instantiating and resizing the components
		
		JFrame frame = new JFrame("Kevin Bacon Game Game");
		frame.setPreferredSize(fr);
		
		JPanel vertical = new JPanel();
		vertical.setPreferredSize(fr);

		JPanel horizontal = new JPanel();
		horizontal.setPreferredSize(hz);
		horizontal.setLayout(new BoxLayout(horizontal, BoxLayout.X_AXIS));
		
		vertical = new JPanel();
		vertical.setPreferredSize(fr);
		vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
		
		JPanel buttonvertical = new JPanel();
		buttonvertical.setPreferredSize(buttonlist);
		buttonvertical.setLayout(new BoxLayout(buttonvertical, BoxLayout.Y_AXIS));

		JLabel l1 = new JLabel("Keyword 1");
		l1.setPreferredSize(labels);
		l1.setVisible(true);
		
		JLabel l2 = new JLabel("Keyword 2");
		l2.setPreferredSize(labels);
		l2.setVisible(true);
		
		JTextArea search1 = new JTextArea();
		search1.setPreferredSize(sch);
		search1.setText("Enter actor name here");
		search1.setEditable(true);
		search1.setVisible(true);
		
		JTextArea search2 = new JTextArea();
		search2.setPreferredSize(sch);
		search2.setText("Enter actor name here");
		search2.setEditable(true);
		search2.setVisible(true);
		
		JTextArea response = new JTextArea();
		response.setPreferredSize(res);
		response.setEditable(false);
		response.setVisible(true);
		
		response.requestFocus();
		
		JScrollPane sp = new JScrollPane(response);
		sp.setPreferredSize(scr);
		sp.setVerticalScrollBar(new JScrollBar());
		sp.setVerticalScrollBarPolicy(sp.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(sp.HORIZONTAL_SCROLLBAR_NEVER);
		//could not get it to scroll enough I think, gets cut off because window reached end of screen?????
		
		
		//button to run bfs, takes in both search box parameters
		JButton search = new JButton("Search");
		search.setPreferredSize(button);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> ans = graph.bfs(search1.getText(), search2.getText());
				response.setText("");
				for(String s : ans) {
					response.append(s);
					response.append("\n");
				}
				response.repaint();
			}

			
		});
		
		//button to run bestfriends method, i've made it optional to run on one or two search box parameters
		//they are individually treated
		JButton BF = new JButton("Find best friends");
		BF.setPreferredSize(button);
		BF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				response.setText("");
				
				if(!search1.getText().equals("")) {
					response.append(graph.bestFriend(search1.getText()) + "\n");
				}
				if(!search2.getText().equals("")) {
					response.append(graph.bestFriend(search2.getText())+"\n");
				}
				
				response.repaint();
			}

			
		});
		
		//same as above, individually treated, if no parameters, it will simply return the most popular in the whole graph
		JButton popular = new JButton("Most popular actor");
		popular.setPreferredSize(button);
		popular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				response.setText("");
				response.append(graph.mostPopular() + "\n");
				
				if(!search1.getText().equals("")) {
					response.append(graph.mostPopular(search1.getText()) + "\n");
				}
				if(!search2.getText().equals("")) {
					response.append(graph.mostPopular(search2.getText())+"\n");
				}
				
				response.repaint();
				
			}

			
		});
		
		//runs all previous functions on a randomly selected person, doesn't run bfs
		//this really wasn't a function haha
		JButton random = new JButton("random");
		random.setPreferredSize(button);
		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				response.setText("");
				String person = graph.random();
				response.append("You have chosen " + person +" \n");
				response.append(graph.mostPopular(person) + "\n");
				response.append(graph.bestFriend(person)+"\n");
				
				response.repaint();
				
			}

			
		});
		
		//here's the third function, searching for actors who have the segment of substring in their name and returning a list
		//However I think the scrollpane doesnt scroll far enough to display all of it for some reason
		//only use first search box
		JButton query = new JButton("query");
		query.setPreferredSize(button);
		query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!search1.getText().equals("")) {
					ArrayList<String> ans = graph.queryActors(search1.getText());
					if(ans.size()==0) {
						response.setText("0 search results\nno matches found");
					}
					else response.setText(ans.size()+" search results \n");
					
					for(String s : ans) {
						response.append(s);
						response.append("\n");
					}
				}
				else {
					response.setText("Illegal input entered");
				}
				response.repaint();
				
			}

			
		});
		
		buttonvertical.add(search);
		buttonvertical.add(popular);
		buttonvertical.add(BF);
		buttonvertical.add(random);
		buttonvertical.add(query);
		
		horizontal.add(l1);
		horizontal.add(search1);
		horizontal.add(l2);
		horizontal.add(search2);
		horizontal.add(buttonvertical);
		
		vertical.add(horizontal);
		vertical.add(sp);
//		sp.setVisible(true);
		//adding everything to the main JPanel
		
		//setting the original display text to "instructions", read them as well
		
		response.setText(Integer.toString(graph.size()) + " vertices \n");
		response.append("Enter two actor's names to search how they are connected \n");
		response.append("Enter in (an) actor(s) names and press find best friend to search their respective best friends \n");
		response.append("Press mostPopular without inputs to find the most popular actor, input (an) actor(s) to find their most popular neighbour \n");
		response.append("press random to find a random person and find their best friends and most popular neighbors");
		
		//adding everything to main frame and displaying
		
		frame.add(vertical);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
		
	}
	
	
	//reader method, uses .split to associate actors/movies to their ids in the form of 2 arraylists of string array
	//then using the movie actors document, read in the connections
	//bond per movie: everyone in it with everyone in it
	//connect method adds them if they didnt exist
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
		
//		int count = 0;
		for(int i=0; i<movieactors.size()-1; i++) {	
			if(movieactors.get(i+1)[0].equals(movieactors.get(i)[0])){
				for(int j=i+1; j<movieactors.size(); j++) {
					if(movieactors.get(j)[0].equals(movieactors.get(i)[0])) {
						temp.connect(idname.get(movieactors.get(i)[1]), idname.get(movieactors.get(j)[1]), idmovie.get(movieactors.get(i)[0]));
//						count++;
					}
					else break;
				}
			}
		}
		
//		System.out.println(count);
		
		return temp;
		
	}
}
