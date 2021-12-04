
package GraphicsEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Editor {
	/*
	graphics editor by jason wang
	
	here i am creating the components for the graphics editor
	
	main = the window that everything goes on
	
	I am using arrays of panels to "organize" them into sections (rows of buttons and textareas)
	
	with much support from mr. friedman, andria bao, and the rest of my class. this actually wont be possible without everyone my brain cant code math AT ALL haha
	*/
	JFrame main;
	//5 panels
	JPanel yAxisContainer,canvas;
	JPanel[] xAxisPanels;
	//6 Text Area
	
	JTextArea[] editable; //array of text area and text label to plug into panel array
	JLabel[] fixed;
	
	//10 Buttons
	JButton[] x1, x2, x3; //array of buttons to plug into panel array (x1 -> panel row 1)
	
	private HashMap<String, Boolean> type = new HashMap<>();
	
	//creating a string boolean hashmap in order to check whether if certain modes are on or off
	
	
	//instantiating fixed dimensions
	private final int WIDTH = 1200, LENGTH = 1000, XAXISHEIGHT = 50, CANVASHEIGHT = LENGTH-(4*XAXISHEIGHT), BUTTONSIZE = 80;
	
	//for mouseListeners
	private int startX, startY, endX, endY, minVal;
	
	//more boolean variables to help the hashmap (some modes needs to setup, and THEN loop through methods)
	private boolean moveShape, resizeShape, perfect;
	
	// instantiating various variables to be used for creating shapes
	private Color color = Color.BLACK;
	
	private Font font;
	
	//main list of shapes to loop and draw from, last is most front, first is most back (in terms of display
	
	private ArrayList<Shape> shapes = new ArrayList<>();

	
	/*
		two arraylists of shape-list to keep track of undo and redo
		my idea for these two functions is that it is in chronological order
		undo (past) shapes (present) redo (future)
		whenever u edit (mouse released) u save a copy of shapes into undo (the past)
		when u go back into undo, u set shapes equal to the last of undo (the latest instance of the past) and remove it from undo
			move shapes into redo first
		when u go to redo, move shapes into undo's last, shapes = redo's last
		use try catch to display dialog in case u go out of bounds
	*/
	
	private ArrayList<ArrayList<Shape>> undo = new ArrayList<ArrayList<Shape>>();
	
	private ArrayList<ArrayList<Shape>> redo = new ArrayList<ArrayList<Shape>>();
	
	//past notes
	
	//make a <ArrayList<Shape>> map called undo and another one called redo
	//at the end of mouse released add the current shapelist to undo
	//if undo button is pressed, set shapelist to last iteration of undomap, remove the last iteration, and move it to redo
	
	//used for move function as I am bringing it from one method to a new method
	private Shape moving = null;
	
	public Editor() {
		
		//a more brute force way of creating my mode-on/off check map
		//everything starts at off
		String[] temporary = new String[] {"undo","save","redo","circle", "rectangle", "line", "color", "text", "delete", "move", "front", "back", "pen", "reset", "copy"};
		for(String s : temporary) {
			type.put(s, false);
		}
		
		//setting up components
		main = new JFrame("Graphics Editor GUI");
		main.setPreferredSize(new Dimension(WIDTH,LENGTH));
		
		//drawing canvas
		canvas = new JPanel(){
			public void paint(Graphics g) {
				
				//overriding canvas draw to also paint shapeslist
				super.paint(getGraphics());
				//paint background
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, Editor.this.WIDTH, CANVASHEIGHT);
				//shapes
				for(Shape s : shapes) {
					s.draw(g);
				}
				
				main.getContentPane().repaint();
			}
		};
		
		//set canvas dimensions
		canvas.setPreferredSize(new Dimension(WIDTH, CANVASHEIGHT));
				
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			//treat like while loop
				if(perfect && !(shapes.get(shapes.size()-1) instanceof Line)) {
					//perfect is when you hold shift and the width + height becomes equal
					//for example, an oval becomes a circle and a rectangle becomes a square
					//I do recognize that this is very finnicky (i dont know how to spell it) when you hold the cursor too close to the shape (within it's width and height in all 4 quadrants)
					//it will start flickering around (and that is due to my if statements) since when it flips due to the cursor being behind the shape, the cursor becomes in front of it again
					//this leads to an endless flickering that only stops when the mouse stops moving
					//however when dragged far enough away the if statements wont trigger repeatedly anymore and will function properly
					if(e.getY()<shapes.get(shapes.size()-1).y && e.getX()<shapes.get(shapes.size()-1).x) {
						shapes.get(shapes.size()-1).resize(startX, startY, startX-minVal, startY-minVal);
						return;
					}
					else if(e.getY()<shapes.get(shapes.size()-1).y && e.getX()>shapes.get(shapes.size()-1).x) {
						shapes.get(shapes.size()-1).resize(startX, startY, startX+minVal, startY-minVal);
						return;
					}
					else if(e.getX()<shapes.get(shapes.size()-1).x && e.getY()>shapes.get(shapes.size()-1).y) {
						shapes.get(shapes.size()-1).resize(startX, startY, startX-minVal, startY+minVal);
						return;
					}
					else
						shapes.get(shapes.size()-1).resize(startX, startY, startX+minVal, startY+minVal);
				}
				//when turned on will constantly resize shapes to mouse cursor until off
				if(resizeShape) {	
					shapes.get(shapes.size()-1).resize(startX, startY, endX=e.getX(), endY=e.getY());
				}
				//when turned on will constantly move shapes to mouse cursor until off
				if(moveShape) {
					if(moving!=null) {
						moving.move(e.getX(), e.getY());
					}
				}
				main.getContentPane().repaint();
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			//used for perfect method, holding shift turns mode on
			//pause resize methods when using perfect since resize is changing height and width independently
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
					perfect=true;
					resizeShape=false;
					minVal = Math.min(Math.abs(endX-shapes.get(shapes.size()-1).x), Math.abs(endY-shapes.get(shapes.size()-1).y));
				}
			}

			@Override
			//releasing turns mode off and unpauses resize
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
					perfect=false;
					resizeShape=true;
				}
			}
			
		});
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			//single action commands, used for creation / one time operations
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				//creates x,y of mouse for further uses
				startX = e.getX();
				startY = e.getY();
				
				//creating pen and using the resize method to add to pen
				if(type.get("pen")) {
					shapes.add(new Pen(startX,startY,0,0,Integer.parseInt(editable[2].getText()), color));
					resizeShape=true;
				}
				
				//deleting the top shape through looping and removing from the shape list 
				//(which will stop it from being drawn (which will then make it magically disappear))
				//uses the isOn method to select shapes that contains the cursor coordinates
				if(type.get("delete")) {
					for(Shape s : shapes) {
						if(s.isOn(e.getX(), e.getY())) {
							shapes.remove(s);
							break;
						}
					}
				}
				
				//out of a selection of shapes that contains the cursor (isOn)
				//move the bottom shape to the front by removing and adding it to the back of the list (which appears in front since its the latest drawn shape)
				if(type.get("front")) {	
					Shape temp = null;
					for(Shape s : shapes) {
						if(s.isOn(e.getX(), e.getY())) {
							temp=s;
							break;
						}
					}
					if(temp!=null) {
						shapes.remove(temp);
						shapes.add(temp);
					}
				}
				
				//out of a selection of shapes that contains the cursor (isOn)
				//move the front shape to the back by removing and adding it to the front of the list (which appears in the back since its the first drawn shape)
				if(type.get("back")) {
					Shape temp = null;
					for(Shape s : shapes) {
						if(s.isOn(e.getX(), e.getY())) {
							temp=s;
						}
					}
					if(temp!=null) {
						shapes.remove(temp);
						shapes.add(0, temp);
					}
				}
				
				//creation of 4 shapes: circle (really an ellipse), rectangle, line, and text
				if(type.get("circle")==true)	{
					shapes.add(new Circle(startX, startY, 0, 0, color));
					resizeShape=true;
				}
				if(type.get("rectangle")==true)	{
						shapes.add(new Rectangle(startX, startY, 0, 0, color));
						resizeShape=true;
				}
				if(type.get("line")) {
						shapes.add(new Line(startX, startY, startX, startY, color, Integer.parseInt(editable[2].getText())));//editable[2] is linewidth
						resizeShape=true;
				}
				//after creation want to turn resize method on until mousereleased to control shape size in real time
				if(type.get("text")) {
					font = new Font("Times New Roman", Font.PLAIN, Integer.parseInt(editable[0].getText()));//editable[0] is textsize
					shapes.add(new Text(startX, startY, font, color, editable[1].getText()));
					resizeShape=false;//cant resize textboxes as it's size is fixed to textsize
				}
				
				//just like resize, move is a looping function so I am using a boolean to monitor its on/off state, while mouse pressed = on, released = off
				if(type.get("move")) {
					
					for(Shape s : shapes) {
						if(s.isOn(startX, startY)) {
							moving=s;
							//get top shape and move
						}
					}
					if(moving!=null) { //avoiding null pointer exception
						shapes.remove(moving);
						shapes.add(moving);
						moveShape=true;
						resizeShape=false;
					}
				}
				
				//same as move method but its moving a copied version (find the top shape but make a copy and then do the same but to the copy)
				if(type.get("copy")) {
					Shape temp = null;
					for(Shape s : shapes) {
						
						if(s.isOn(e.getX(), e.getY())) {
							temp=s;
						}
						
					}
					if(temp!=null)
						shapes.add(moving=temp.copy());
					
					moveShape=true;
					resizeShape=false;
				}
				canvas.requestFocus();
				
			}

			@Override
			//turn everything off and reset everything, then run copyshapes to store a copy of the list (used for undo)
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				resizeShape=false;
				moveShape = false;
				moving=null;
				copyshapes();
				//if(type.get("line"))
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
		
		
		//yaxis container
		yAxisContainer = new JPanel();
		yAxisContainer.setLayout(new BoxLayout(yAxisContainer, BoxLayout.Y_AXIS));
		
		//xaxis panels for buttons/textareas
		//these jpanels will horizontally hold the jbuttons and then be held vertically in another panel for organization
		//using array create a ton of buttons using only one loop, I thought it was cool
		//need to give it a button name during creation
		xAxisPanels = new JPanel[] {new JPanel(), new JPanel(), new JPanel()};
		x1 = new JButton[] {new JButton("circle"), new JButton("rectangle"), new JButton("line"), new JButton("save")};
		x2 = new JButton[] {new JButton("color"), new JButton("text"), new JButton("reset"), new JButton("copy"), new JButton("undo"), new JButton("redo")};
		x3 = new JButton[] {new JButton("delete"), new JButton("front"), new JButton("back"), new JButton("move"), new JButton("pen")};
		fixed = new JLabel[] {new JLabel("textSize"), new JLabel("textContent"), new JLabel("lineWidth")};
		editable = new JTextArea[] {new JTextArea("10"), new JTextArea("amount2"), new JTextArea("10")};
		
		//the loop used for creating everything in the array in one go, you will see this "method" get reused for x2 and x3
		for(JButton button : x1) {
			//setting dimensions and then add a action listener and override it (all the button need)
			button.setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//override as such (when a button is pressed go through the type map (of button names mapped to boolean) and turn the corresponding key's
					//value on, while turning everything else
					for(String s : type.keySet()) {
						if(s.equals(button.getText()))
							type.put(s, true);
						else
							type.put(s, false);
						
						//if a button has an instant function include it here
						//saving as File class inside Java folder
						if(type.get("save")) {
							//convert to BufferedImage and use the createGraphics method to get graphics2d of the image
							//then use printAll to print everything in img's graphics2d (ish) (im not exactly sure how this step works but it updates something
								//that allows it to be saved)
							//then close the graphics2d and use imageio class to make a file to write the bufferedimage onto it (aka saving canvas as a file)
							BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
							Graphics2D g2d = img.createGraphics();
							canvas.printAll(g2d);
							g2d.dispose();
							try {
								ImageIO.write(img, "png", new File("savedPicture.png"));
							} catch (IOException ioe) {
								ioe.printStackTrace();
							}
							
						}
					}
					
				}
				
			});
			xAxisPanels[0].add(button); //finally add each finalized button to the array of jpanels
		}
		
		//same method idea, I'll just explain the specific unique functions
		for(JButton button : x2) {
			button.setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(String s : type.keySet()) {
						if(s.equals(button.getText()))
							type.put(s, true);
						else
							type.put(s, false);
					}
					
					//uses JColorChooser import to choose a color
					//showDialog creates a windowed dialog box showing a precoded color chooser
					//set the instance variable color to the choice
					if(type.get("color")) {
						JColorChooser cc = new JColorChooser();
						color = cc.showDialog(null, "Pick a Color", color);
					}
					//reset = clearing shape list = no shapes to draw = blank white canvas
					if(type.get("reset")) {
						shapes.clear();
					}
					//when you mouse release you actually save the current shape list to undo meaning you want shapes list to be second to last (as the last is the same as shapes list)
					//therefore I will remove last of undo to redo (for redo's function) and then use the now last (previously second to last) in undo to set shapes list equal to
					//have to use copying whenever I "set equal" to anything so they arent the same but have components that have the same values
						//user will see the same thing but the computer will treat them separately (which allows undo to function)
					if(type.get("undo")) {
						redo.add(undo.remove(undo.size()-1));
						try {
							shapes.clear();
							for(Shape s : undo.get(undo.size()-1)) {
								shapes.add(s.copy());
							}
							
//							shapes=undo.remove(undo.size()-1);
						} catch (IndexOutOfBoundsException iooe) { //catch this exception when u try going in undo too far to the point where all shape disappears
							iooe.printStackTrace();
							JOptionPane.showMessageDialog(canvas, "u cannot undo further");
						}
						main.getContentPane().repaint();
					}
					//extremely similar to undo except it is last instead of second to last of the redo list, so we only remove 1 time
					//basically sets shapes equal to the "last of undo" that I removed from using the undo function
					
					if(type.get("redo")) {
						undo.add(redo.remove(redo.size()-1));
						try {
							shapes.clear();
							for(Shape s : undo.get(undo.size()-1)) {
								shapes.add(s.copy());
							}
//							shapes=redo.remove(redo.size()-1);
						} catch (IndexOutOfBoundsException iooe) {
							iooe.printStackTrace();
							JOptionPane.showMessageDialog(canvas, "u cannot redo further");
						}
						main.getContentPane().repaint();
					}
					
				}
				
			});
			xAxisPanels[1].add(button);
		}
		
		//a representation of the loop but with no unique functions
		for(JButton button : x3) {
			button.setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(String s : type.keySet()) {
						if(s.equals(button.getText())) {
							type.put(s, true);
					}
						else
							type.put(s, false);
					}
					
				}
				
			});
			xAxisPanels[2].add(button);
		}
		
		//using a loop to include the textareas and jlabels after creating its settings and dimensions
		
		JPanel x4 = new JPanel();
		
		//setting dimensions of text jpanel
		x4.setLayout(new BoxLayout(x4, BoxLayout.X_AXIS));
		x4.setPreferredSize(new Dimension(Editor.this.WIDTH,Editor.this.XAXISHEIGHT));
		for(int i=0; i<editable.length; i++) {
			fixed[i].setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			editable[i].setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			//fixed[i].setEditable(false); this is when i was trying out all textareas but jlabel was more convenient and familiar for me
			editable[i].setEditable(true);
			x4.add(fixed[i]);
			x4.add(editable[i]);
		}
		
		//set dimensions of the button panels and add them to another panel that holds them vertically (so 4 rows of buttons)
		for(JPanel p : xAxisPanels) {
			p.setPreferredSize(new Dimension(Editor.this.WIDTH, XAXISHEIGHT));
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			yAxisContainer.add(p);
		}
		
		//add the textarea panel
		yAxisContainer.add(x4);
		//then add the canvas to panel last so it appears at the bottom
		yAxisContainer.add(canvas);
		
		//finally add the vertical container to the jframe and update the frame's settings
		main.add(yAxisContainer);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		main.setVisible(true);
		canvas.requestFocus();
		
		//save the blank screen for undo list
		undo.add(shapes);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Editor();
	}
	
	//run at the end of every mouse action (mouse released)
	public void copyshapes() {
		
		//create a copy list and copy everything from shape list into it
		ArrayList<Shape> copy = new ArrayList<>();
		
		
		for(Shape s : shapes) {
			copy.add(s.copy());
		}
		
		//then add it to undo
		undo.add(copy);
	}

}
