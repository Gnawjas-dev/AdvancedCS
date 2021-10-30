package BasicGraphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AIChatBot {
	
	JFrame frame;
	JPanel panel;
	JTextArea text, enter;
	JButton button;
	JScrollPane jp;
	private final int WIDTH = 600, HEIGHT = 600, BHEIGHT = 100;
	
	public AIChatBot() {
		new GUI();
	}
	
	public class GUI implements ActionListener{ //copy into war later
			
			public GUI() {
				
				frame = new JFrame();
				frame.setSize(new Dimension(WIDTH,HEIGHT));
				
				panel = new JPanel();
				
	//			scroll = new JScrollPane();
				text = new JTextArea();jp = new JScrollPane(text);
				enter = new JTextArea();
				button = new JButton("Start");
				button.addActionListener(this);
				button.setPreferredSize(new Dimension(WIDTH,BHEIGHT));
				panel.setPreferredSize(new Dimension(WIDTH, HEIGHT/2));
				jp.setPreferredSize(new Dimension(WIDTH, HEIGHT/2));
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				
	//			panel.setBorder(BorderFactory.createEmptyBorder(frame.getWidth(),frame.getHeight(),frame.getWidth(), frame.getHeight()));
	//			panel.setLayout(new GridLayout(1,1));
				
				text.setText("Hi, I am ChatBot.");
				text.setPreferredSize(new Dimension(WIDTH,HEIGHT/2));
				text.setLineWrap(true);
				text.setWrapStyleWord(true);
				text.setEditable(false);
				text.setVisible(true);
				
				enter.setText("Type Something Here");
				enter.setPreferredSize(new Dimension(WIDTH,HEIGHT/2-BHEIGHT));
				enter.setLineWrap(true);
				enter.setWrapStyleWord(true);
				enter.setEditable(true);
				enter.setVisible(true);
	
				jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	//			scroll.add(text);
				
				
				panel.add(jp);
				panel.add(enter);
				panel.add(button);
				
				frame.add(panel);
				
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("AIChatBot32Bit.exe");
				frame.pack();
				frame.setVisible(true);
				
				frame.update(text.getGraphics());
			}
				@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				button.setText("Chat!");
				text.append("\n");
				text.append(enter.getText());
				text.append("\n");
				talk();
				update();
			}
				
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AIChatBot();
	}

	public void talk() {
		// TODO Auto-generated method stub
		text.append("Hi");
	}

	public void update() {
		// TODO Auto-generated method stub
		frame.update(text.getGraphics());
		
	}

}
