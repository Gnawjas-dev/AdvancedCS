package Translator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Translator{
	
	HashMap <String, String>myMap = new HashMap<>();
	JFrame frame;
    JPanel buttonPanel, textPanel;
    JLabel resultL, searchbar;
    JTextArea result, search;
    JButton searchButton, exitButton;
	
	public void readFile() throws IOException {
		
		FileReader in = new FileReader("EnglishToArabicDictionary.txt");
		BufferedReader reader = new BufferedReader(in);
		reader.readLine();
		String line;
		String secondLine;
		
		while((line=reader.readLine())!=null) {
			secondLine=reader.readLine()
;			myMap.put(line, secondLine);
					//english, arabic
		}
		
		System.out.println(myMap);
		
	}
	
	public Translator() {
		
		frame = new JFrame("2021 English to Arabic Dictionary, programmed by Jason");
		frame.setPreferredSize(new Dimension(600,600));
        buttonPanel = new JPanel();
        textPanel = new JPanel();
        resultL = new JLabel("result");
        searchbar = new JLabel("searchbar");
        result = new JTextArea("Result shows up here");
        search = new JTextArea("Search up word here");
        searchButton = new JButton("search");
        exitButton = new JButton("close");
        
        searchButton.setAction(new AbstractAction ("search") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				search();
			}
        	
        });
        
        exitButton.setAction(new AbstractAction ("close") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(1);;
			}
        	
        });
        
        

        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setLayout(new FlowLayout());

		
		search.setText("Enter word here");
		search.setLineWrap(true);
		search.setWrapStyleWord(true);
		search.setEditable(true);
		search.setVisible(true);
		
		result.setText("Results show up here");
		result.setLineWrap(true);
		result.setWrapStyleWord(true);
		result.setEditable(false);
		result.setVisible(true);
		
		textPanel.add(searchbar);
        textPanel.add(search);
        textPanel.add(resultL);
        textPanel.add(result);
        buttonPanel.add(searchButton);
        buttonPanel.add(exitButton);
        frame.add(textPanel, BorderLayout.PAGE_START);
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
		frame.update(search.getGraphics());
		frame.update(result.getGraphics());
		
		try {
		readFile();
		} catch (IOException ioe) {
			result.setText("An error occurred\nSearch again");
			System.out.println("Error");
		}
		
		search();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Translator();
	}
		public void search() {
			// TODO Auto-generated method stub
			//Search here and return results
			result.setText(myMap.get(search.getText().toLowerCase()));
			if(result.getText()==null)
				result.setText("The word you searched isn't included in the dictionary");
			frame.update(result.getGraphics());
			return;
		}
}

	
