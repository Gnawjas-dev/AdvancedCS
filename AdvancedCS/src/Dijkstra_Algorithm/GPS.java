package Dijkstra_Algorithm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Dijkstra_Algorithm.DijkstraGraph.Edge;
import Dijkstra_Algorithm.DijkstraGraph.Vertex;

public class GPS {
	
	//I have everything inside one file
	//Left click on an empty space to create a point
	//Hovering over a point turns it red
	//Clicking on two circles turns it red
	//You cant exit out of connecting until you clicked the second circle (think before you act!)
	//Saves automatically
	//When you run next time it will ask if you want to load
	
	//right click on 2 points to dijkstra
	//File Chooser to implement custom maps eyyyyyyyyyyyyy
	
	
	//Global variables
	DijkstraGraph gps;
	JFileChooser jfc;
	JFrame main = new JFrame();
	JPanel yaxis, xtop, xbot;
	boolean add = true, connect = false;
	Vertex cnct = null, dijkstra = null;
	File selectedFile = null;
	
	
	//runner
	public GPS() throws IOException{
		
		//using filechooser to load maps from the user
		jfc = new JFileChooser();
		//gets the directory path
		jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		//opens up the chooser tab
		int result = jfc.showOpenDialog(main);
		
		//check if a file is selected and save it
		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = jfc.getSelectedFile();
		}
		
		//if selected file is still nonexistent then report error and shut down
		//this is in case there's a cancel AND a exit button
		if(selectedFile==null) {
			JOptionPane.showMessageDialog(main,
				    "The map you are trying to upload doesnt exist",
				    "Map not found error",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		//cast file into image to draw
		
		BufferedImage background = ImageIO.read(selectedFile);
		
		//getting the dimensions of the image to draw the jframe around it
		int width = background.getWidth();
		int height = background.getHeight();
		main.setPreferredSize(new Dimension(width, height));
		
		main.setDefaultCloseOperation(main.EXIT_ON_CLOSE);
		
		//this is the drawing panel
		xtop = new JPanel() {
			public void paint(Graphics g) {
				//draws the image at the bottom
				g.drawImage(background, 0, 0, null);
				//calls a draw method in Dijkstra Graph
				gps.draw(g);
			}
		};
		
		//setting dimensions and adding functionality
		xtop.setPreferredSize(new Dimension(width,height));
		xtop.setLayout(new BoxLayout(xtop, BoxLayout.X_AXIS));
		xtop.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//first save x and y from the press
				int x = e.getX();
				int y = e.getY();
				
				//check if its right click or not for dijkstra or just adding/connecting
				if(e.getButton()==MouseEvent.BUTTON3) {
					
					//a rough program to run only when 2 points have been selected
					//loops through all vertices
					for(Vertex v : gps.getGraph().values()) {
						//if cursor was on this point when clicked
						if(v.hover) {
							//if there was no previous points saved, save it
							if(dijkstra==null) {
								dijkstra=v;
							} else {
								//run dijkstra on the two points after resetting all variables used in the function
								for(Vertex v1 : gps.getGraph().values()) {
									v1.path=false;
									for(Edge e1 : v1.neighbors) {
										e1.path=false;
									}
								}
								//dijstra and reset
								gps.dijkstra(dijkstra.info, v.info);
								dijkstra=null;
							}
						}
					}
				}
				//if left click
				else {
					//using hover boolean to activate one of two modes (theres no exitting so you are screwed if you are stuck in one)
					for(Vertex v : gps.getGraph().values()) {
						//basically if you click a point then you cant make a new point until you've connected it
						if(v.hover) {
							connect=true;
						}
					}
					
					//adds a point on the map with a user generated name
					if(!connect) {
						String result = JOptionPane.showInputDialog(main, "Enter point name");
						//this checks for exited and cancelled response
						if(result!=null)
							gps.add(result, x, y);
					}
					//connecting points
					if(connect) {
						
						//same 2 click check program from before
						for(Vertex v : gps.getGraph().values()) {
							//if you clicked on a point
							if(v.hover) {
								//if you've already clicked
								if(cnct!=null) {
									//connect the two points and reset variables
									gps.connect((String)cnct.info, (String)v.info);
									cnct=null;
									connect=false;
								} else {
									//if you didn't then save
									cnct = v;
								}
							}
						}
					}
				}
				
				
				
				
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
		
		xtop.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				int mousex = e.getX();
				int mousey = e.getY();
				
				//when you are moving your mouse it will check if its hovered over a point or not
				gps.checkHover(mousex, mousey);
				
				//this is a pretty constant repaint
				xtop.repaint();
			}
			
		});
		

		main.add(xtop);
		
		main.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
				//when the jframe is closing, save information into a text file
				try {
					gps.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		
		main.setVisible(true);
		main.pack();
		
		gps = new DijkstraGraph();
		
		//runs this after choosing a map, then if yes it will run the read method on the saved txt file
		if (JOptionPane.showConfirmDialog(null, "Load previous points?", "Resume?",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		    // yes option
			gps.read();
		} 
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new GPS();
	}

}
