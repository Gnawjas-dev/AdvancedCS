package BasicGraphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasicGraphics {
	
	private Image img;
	private int width = 600, height = 550;
	private final int TEXTBOXSIZE = 50;
	
	public BasicGraphics(){
		
		img = Toolkit.getDefaultToolkit().getImage("cursed mmrph.png");
		
		JFrame frame = new JFrame("image");
		frame.setSize(width, height+TEXTBOXSIZE+10);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel container = new JPanel();
		
		container.setPreferredSize(frame.getSize());
		
		JPanel picture = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(img, 0, 0, width, height, this);
			}
		};
		
		picture.setPreferredSize(new Dimension(width, height));
		
		JPanel textPanel = new JPanel(); 
		
		textPanel.add(new JLabel("I am an Image with a Text"));
		
		textPanel.setPreferredSize(new Dimension(width, TEXTBOXSIZE));
		
		container.add(picture);
		container.add(textPanel);
		frame.add(container);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new BasicGraphics(); 
	}
	
}
