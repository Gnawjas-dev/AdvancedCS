package CustomLinkedList;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI implements ActionListener{ //copy into war later
	
	JFrame frame;
	
	JPanel panel;
	
	JButton button;
	
	JTextArea text;
	
	public GUI() {
		
		frame = new JFrame();
		
		panel = new JPanel();
		
		button = new JButton("Continue");
		button.addActionListener(this);
		
		text = new JTextArea();
		
		text.setText("test");
		text.setBounds(0, 0, 250, 250);
		text.setLineWrap(true);
		text.setEditable(false);
		
		panel.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
		panel.setLayout(new GridLayout(0,1));
		panel.add(text);;
		panel.add(button);
		
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("War Game GUI");
		frame.pack();
		frame.setVisible(true);
		

	}
		@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//turn();
		//use text.append(String s); to add to end of line
		// use "\n" at the end of line to make new like or text wrap
	}
		

	public static void main(String[] args) {
		new GUI();
	}



}
