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
	private int prevRan=-1;
	
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
		int random = (int)(Math.random()*5)+0;
		if(random!=prevRan) {
			prevRan=random;
		} else {
			random += (int)(Math.random()*(5-random))+random;
		}
			
			if(random==0)
				text.append("What are you going to do this weekend?");
			else if(random==1)
				text.append("What are you going to eat today");
			else if(random==2)
				text.append("What is your favorite animal? I love crows! They are so omnious");
			else if(random==3)
				text.append("What is your favorite snack? I like french fries. ");
			else if(random==4)
				text.append("What class do you have next?");
			else if(random==5)
				text.append("Do we have lunch at A block or B block?");

	}

	public void update() {
		// TODO Auto-generated method stub
		frame.update(text.getGraphics());
		
	}

}
