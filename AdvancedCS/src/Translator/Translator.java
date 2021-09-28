package Translator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Translator {//implements ActionListener
	
	TreeMap <String, String>myMap = new TreeMap<>();
	JFrame frame;
	JButton button;
	JTextArea search;
	JTextArea result;
	JPanel panel;
	
	Scanner in = new Scanner(System.in);
	
	public void readFile() throws IOException {
		
		FileReader in = new FileReader("EnglishToArabicDictionary.txt");
		BufferedReader reader = new BufferedReader(in);
		String line;
		
		while((line=reader.readLine())!=null) {
			myMap.put(line, reader.readLine());
					//english, arabic
		}
		
	}
	
	public Translator() {
		
//		frame = new JFrame();
//		frame.setSize(new Dimension(600,600));
//		
//		panel = new JPanel();
//		search = new JTextArea();
//		result = new JTextArea();
//		button = new JButton("Start");
//		button.addActionListener(this);
//		button.setPreferredSize(new Dimension(200,200));
//		panel.setPreferredSize(new Dimension(600,600));
//
//		
//		search.setText("Enter word here");
//		search.setPreferredSize(new Dimension(400,200));
//		search.setLineWrap(true);
//		search.setWrapStyleWord(true);
//		search.setEditable(true);
//		search.setVisible(true);
//		
//		result.setText("Results show up here");
//		result.setPreferredSize(new Dimension(600,400));
//		result.setLineWrap(true);
//		result.setWrapStyleWord(true);
//		result.setEditable(false);
//		result.setVisible(true);
//		
//		panel.add(search);
//		panel.add(result);
//		panel.add(button);
//		frame.add(panel);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("Translator");
//		frame.pack();
//		frame.setVisible(true);
//		
//		frame.update(search.getGraphics());
//		frame.update(result.getGraphics());
//		
		try {
		readFile();
		} catch (IOException ioe) {
//			result.setText("An error occurred\nSearch again");
			System.out.println("Error");
		}
		
		search();
	}
	
	public void search() {
		System.out.println("Search for word");
		String search = in.next();
		for(String s : myMap.keySet()) {
			if(s==search) {
				System.out.println(myMap.get(s));
				search();
			}
		}
		System.out.println("The word you searched isn't included in the dictionary");
		search();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Translator();
	}
//	@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			//Search here and return results
//			for(String s : myMap.keySet()) {
//				if(s==search.getText()) {
//					result.setText(s);
//					frame.update(result.getGraphics());
//					return;
//				}
//			}
//			result.setText("The word you searched isn't included in the dictionary");
//			frame.update(result.getGraphics());
//			return;
//		}
}

	
