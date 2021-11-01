package BasicGraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Listeners {
	
	private final int WIDTH = 500, HEIGHT = 500;
	private int XVEL = 10, YVEL = 10;
	private boolean up, right, down, left;
	private int rectX = 0; 
	private int rectY = 0;
	private JFrame frame;
	
	public Listeners() {
		frame = new JFrame("image");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.WHITE);
				g.fillRect(0,0, Listeners.this.WIDTH, Listeners.this.HEIGHT);
				g.setColor(Color.BLACK);
				g.fillRect(rectX, rectY, 50, 50);
				g.drawString("Speed of Square: " + XVEL, 0, Listeners.this.HEIGHT-50);
			}
		};
		
		panel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				rectX=e.getX();
				rectY=e.getY();
				frame.getContentPane().repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		panel.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				if(e.getWheelRotation()<0) {
					XVEL+=e.getScrollAmount();
					YVEL+=e.getScrollAmount();
				}
				if(e.getWheelRotation()>0) {
					XVEL-=e.getScrollAmount();
					YVEL-=e.getScrollAmount();
				}
			}
			
		});
		
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
					rectY-=YVEL;
					frame.getContentPane().repaint();
				}
				if(e.getKeyChar()=='s') {
					rectY+=YVEL;
					frame.getContentPane().repaint();
				}
				if(e.getKeyChar()=='d') {
					rectX+=XVEL;
					frame.getContentPane().repaint();
				}
				if(e.getKeyChar()=='a') {
					rectX-=XVEL;
					frame.getContentPane().repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				frame.getContentPane().repaint();
			}
			
		});
		frame.add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.setVisible(true);
		panel.requestFocus();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Listeners();
	}

}
