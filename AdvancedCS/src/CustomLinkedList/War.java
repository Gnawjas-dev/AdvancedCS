package CustomLinkedList;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class War extends LinkedList{
	
	LinkedList <Card> deck = new LinkedList<>();
	LinkedList <Card> deck1 = new LinkedList<>();
	LinkedList <Card> deck2 = new LinkedList<>();
	Scanner in = new Scanner(System.in);
	JFrame frame;
	JPanel panel;
	
//	JScrollPane scroll;
	
	JButton button;
	
	JTextArea text;
	
	int turn = 0;
	
	public class Card {
		int print;
		String suit;
		
		Card (int print, String suit){
			this.print = print;
			this.suit = suit;
		}
	}
	
	public War() {
		
		String[] suits= new String[] {"Diamonds", "Clubs", "Spades", "Hearts"};
		
		for(int i=0; i<4; i++) {
			for(int j=1; j<=13; j++) {
				deck.add(new Card(j, suits[i]));
			}
		}
		
		new GUI();
		shuffle();
		split();
	}
	
	public class GUI implements ActionListener{ //copy into war later
		
		public GUI() {
			
			frame = new JFrame();
			frame.setSize(new Dimension(600,600));
			
			panel = new JPanel();
//			scroll = new JScrollPane();
			text = new JTextArea();
			button = new JButton("Start");
			button.addActionListener(this);
			button.setPreferredSize(new Dimension(600,100));
			panel.setPreferredSize(new Dimension(600,600));
			
			
			
//			panel.setBorder(BorderFactory.createEmptyBorder(frame.getWidth(),frame.getHeight(),frame.getWidth(), frame.getHeight()));
//			panel.setLayout(new GridLayout(1,1));
			
			text.setText("Welcome to War Games, press continue to start");
			text.setPreferredSize(new Dimension(600,500));
			text.setLineWrap(true);
			text.setWrapStyleWord(true);
			text.setEditable(false);
			text.setVisible(true);

//			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//			scroll.add(text);
			
			
			panel.add(text);
			panel.add(button);
			frame.add(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("War Game GUI");
			frame.pack();
			frame.setVisible(true);
			
			frame.update(text.getGraphics());
		}
			@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			button.setText("Continue");
			turn++;
			run();
			text.append("click continue \n");
			//use text.setText(String s); to add to end of line
			// use "\n" at the end of line to make new like or text wrap
			frame.update(text.getGraphics());
		}
			
	}

	
	public void shuffle() {
		
		for(int i=0; i<deck.size(); i++) {
			
			int j = (int)(Math.random()*deck.size());
			Card temp = deck.get(i);
			
			deck.add(deck.get(j),i);
			deck.remove(i+1);
			deck.add(temp, j);
			deck.remove(j+1);
			
		}
		
	}
	
	public void split() {
		
		for(int i=0; i<deck.size();i++) {
			
			if(i%2==0) {
				deck1.add(deck.get(i));
			}
			
			else {
				deck2.add(deck.get(i));
			}
			
		}
		
	}
	
	
	public void turn() {
			
			Card one = deck1.remove(0);
			Card two = deck2.remove(0);
			
			text.append("player 1's card is: " + one.print + " of " + one.suit + ". \n");
			text.append("player 2's card is: " + two.print + " of "+ two.suit + ". \n");
			
			if(one.print>two.print) {
				
				text.append("player 1's card was greater, he WINS this ROUND \n");
				deck1.add(one);
				deck1.add(two);
				text.append("both cards has been added to player 1's deck \n");
				
			} else if (one.print<two.print) {
				
				text.append("player 2's card was greater, he WINS this ROUND \n");
				deck2.add(one);
				deck2.add(two);
				text.append("both cards has been added to player 2's deck \n");
				
			} else {
				
//				deck1.add(one);
//				deck2.add(two);
				text.append("Oh No! It's a DRAW! Everybody LOSES 1 card \n");

			}
			
		
	}
	
	public void suddendeath() {
		
		Card one = deck1.remove(0);
		Card two = deck2.remove(0);
		
		text.append("player 1's card is: " + one.print + " of " + one.suit + ". \n");
		text.append("player 2's card is: " + two.print + " of "+ two.suit + ". \n");
		
		if(one.print>two.print) {
			
			text.append("player 1's card was greater, he WINS this GAME \n");
			deck1.add(one);
			deck1.add(two);
			for(int i = 0; i<deck2.size(); i++) {
				deck1.add(deck2.remove(i));
			}
			text.append("All cards has been added to player 1's deck \n");
			
		} else if (one.print<two.print) {
			
			text.append("player 2's card was greater, he WINS this GAME \n");
			deck2.add(one);
			deck2.add(two);
			for(int i = 0; i<deck1.size(); i++) {
				deck2.add(deck1.remove(i));
			}
			text.append("All cards has been added to player 2's deck \n");
			
		} else if(one.print == two.print){
			
//			deck1.add(one);
//			deck2.add(two);
			text.append("Oh No! It's a DRAW! Everybody LOSES the GAME \n");
			deck1.clear();
			deck2.clear();

		}
		text.append("the game has ended \n");
}
	
	public void run() {
		if(deck1.size()>0&&deck2.size()>0) {
			text.setText("turn " + turn + ": \n");
			
			if(turn==50) {
				
				text.append("sudden death mode activated \n");
				text.append("whoever wins the next turn wins the game \n");
				text.append("if it's a draw, everyone loses \n");
				
				suddendeath();
				
			} else if (turn>50) {
				System.exit(1);
			} else {
				text.append("player one has " + deck1.size() + " cards in his deck \n");
				text.append("player two has " + deck2.size() + " cards in his deck \n");
	//			not pausing on normal
				turn();
			}
			
		}
		
		end();
		
	}
	
	public void end() {
		
		if(deck1.size()==0&&deck2.size()>0)
			text.setText("player 2 wins with " + deck2.size() + " cards left \n");
		else if (deck2.size()==0 && deck1.size()>0)
			text.setText("player 1 wins with " + deck1.size() + " cards left \n");
		else if(deck1.size()==0 && deck2.size()==0)
			text.setText("it is a DRAW, EVERYONE LOSES! HAHAHAHAHA");
		frame.update(text.getGraphics());
		frame.remove(button);
		JButton end = new JButton("End");
		end.setPreferredSize(button.getPreferredSize());
		end.setEnabled(false);
		frame.add(end);
		frame.update(end.getGraphics());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		HashMap  <String, Integer> myMap = new HashMap<>();
//		myMap.put("Jason", 16);
//		myMap.put("James", 99999999);
//		myMap.put("James", 0);
//		
//		myMap.remove("James");
//		
//		for(String i : myMap.keySet() )
//			
//		for(Integer i : myMap.values())
//		
//		myMap.get("Jason");
		new War();
	}


}
