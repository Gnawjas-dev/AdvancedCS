package BasicGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Listeners {
	
	private final int WIDTH = 500, HEIGHT = 500;
	private boolean up, right, down, left;
	private int rectX = 0; 
	private int rectY = 0;
	
	public Listeners() {
		JFrame frame = new JFrame("image");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.BLACK);
				g.fillRect(rectX, rectY, 50, 50);
			}
		};
		panel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				e.getKeyChar();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()=='w') {
					up=true;
				}
				if(e.getKeyChar()=='s') {
					down=true;
				}
				if(e.getKeyChar()=='d') {
					right=true;
				}
				if(e.getKeyChar()=='a') {
					left=true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar()=='w') {
					up=false;
				}
				if(e.getKeyChar()=='s') {
					down=false;
				}
				if(e.getKeyChar()=='d') {
					right=false;
				}
				if(e.getKeyChar()=='a') {
					left=false;
				}
			}
			
		});
		frame.add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.setVisible(true);
		panel.requestFocus();
		
		while(true) {
			move();
			frame.getContentPane().repaint();
		}
	}
	
	public void move() {
		if(up) {
			rectY++;
		}
		if(down) {
			rectY--;
		}
		if(left) {
			rectX--;
		}
		if(right) {
			rectX++;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Listeners();
	}

}
