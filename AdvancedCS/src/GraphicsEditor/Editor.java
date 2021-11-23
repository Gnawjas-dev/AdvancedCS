
package GraphicsEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
public class Editor {
	JFrame main;
	//5 panels
	JPanel yAxisContainer,canvas;
	JPanel[] xAxisPanels;
	//6 Text Area
	
	JTextArea[] editable;
	JLabel[] fixed;
	
	//10 Buttons
	JButton[] x1, x2, x3;
	private HashMap<String, Boolean> type = new HashMap<>();
	
	//instantiating fixed dimensions
	private final int WIDTH = 1200, LENGTH = 1000, XAXISHEIGHT = 50, CANVASHEIGHT = LENGTH-(4*XAXISHEIGHT), BUTTONSIZE = 80;
	
	//for mouseListeners
	private int startX, startY, endX, endY, minVal;
	
	private boolean moveShape, resizeShape, perfect, penmode;
	
	private Color color = Color.BLACK;
	
	private Pen pen;
	
	private Font font;
	
	private ArrayList<Shape> shapes = new ArrayList<>();
	
	private ArrayList<ArrayList<Shape>> undo = new ArrayList<ArrayList<Shape>>();
	
	private ArrayList<ArrayList<Shape>> redo = new ArrayList<ArrayList<Shape>>();
	
	//make a <ArrayList<Shape>> map called undo and another one called redo
	//at the end of mouse released add the current shapelist to undo
	//if undo button is pressed, set shapelist to last iteration of undomap, remove the last iteration, and move it to redo
	
	private Shape moving = null;
	
	public Editor() {
	
		String[] temporary = new String[] {"undo","redo","circle", "rectangle", "line", "color", "text", "delete", "move", "front", "back", "pen", "reset", "copy"};
		for(String s : temporary) {
			type.put(s, false);
		}
		
		main = new JFrame("Graphics Editor GUI");
		main.setPreferredSize(new Dimension(WIDTH,LENGTH));
		
		//drawing canvas
		canvas = new JPanel(){
			public void paint(Graphics g) {
				super.paint(getGraphics());
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, Editor.this.WIDTH, CANVASHEIGHT);
				
				for(Shape s : shapes) {
					s.draw(g);
				}
				if(pen!=null)
					pen.draw(g);
				main.getContentPane().repaint();
			}
		};
		
		canvas.setPreferredSize(new Dimension(WIDTH, CANVASHEIGHT));
				
		canvas.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(perfect && !(shapes.get(shapes.size()-1) instanceof Line)) {
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
				
				if(resizeShape) {	
					shapes.get(shapes.size()-1).resize(startX, startY, endX=e.getX(), endY=e.getY());
				}
				if(moveShape) {
					if(moving!=null) {
						moving.move(e.getX(), e.getY());
						
					}
				}
				if(penmode) {
					shapes.get(shapes.size()-1).move(e.getX(), e.getY()); //is actually pen's add method
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
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
					perfect=true;
					resizeShape=false;
					minVal = Math.min(Math.abs(endX-shapes.get(shapes.size()-1).x), Math.abs(endY-shapes.get(shapes.size()-1).y));
				}
			}

			@Override
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
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				startX = e.getX();
				startY = e.getY();
				
				if(type.get("pen")) {
					penmode=true;
					shapes.add(new Pen(startX,startY,0,0,Integer.parseInt(editable[2].getText()), color));
				}
				
				if(type.get("delete")) {
					for(Shape s : shapes) {
						if(s.isOn(e.getX(), e.getY())) {
							shapes.remove(s);
							break;
						}
					}

				}
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
				
				if(type.get("circle")==true)	{
					shapes.add(new Circle(startX, startY, 0, 0, color));
					resizeShape=true;
				}
				if(type.get("rectangle")==true)	{
						shapes.add(new Rectangle(startX, startY, 0, 0, color));
						resizeShape=true;
				}
				if(type.get("line")) {
						shapes.add(new Line(startX, startY, 0, 0, color, Integer.parseInt(editable[2].getText())));
						resizeShape=true;
				}
				if(type.get("text")) {
					font = new Font("Times New Roman", Font.PLAIN, Integer.parseInt(editable[0].getText()));
					shapes.add(new Text(startX, startY, font, color, editable[1].getText()));
					resizeShape=false;
				}
				if(type.get("move")) {
					
					for(Shape s : shapes) {
						if(s.isOn(startX, startY)) {
							moving=s;
						}
					}
					shapes.remove(moving);
					shapes.add(moving);
					
					moveShape=true;
					resizeShape=false;
				}
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
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				resizeShape=false;
				moveShape = false;
				moving=null;
				undo.add(shapes);
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
		
		xAxisPanels = new JPanel[] {new JPanel(), new JPanel(), new JPanel()};
		x1 = new JButton[] {new JButton("circle"), new JButton("rectangle"), new JButton("line")};
		x2 = new JButton[] {new JButton("color"), new JButton("text"), new JButton("reset"), new JButton("copy"), new JButton("undo"), new JButton("redo")};
		x3 = new JButton[] {new JButton("delete"), new JButton("front"), new JButton("back"), new JButton("move"), new JButton("pen")};
		fixed = new JLabel[] {new JLabel("textSize"), new JLabel("textContent"), new JLabel("lineWidth")};
		editable = new JTextArea[] {new JTextArea("10"), new JTextArea("amount2"), new JTextArea("10")};
		
		for(JButton button : x1) {
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
					
				}
				
			});
			xAxisPanels[0].add(button);
		}
		
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
					
					if(type.get("color")) {
						JColorChooser cc = new JColorChooser();
						color = cc.showDialog(null, "Pick a Color", color);
					}
					if(type.get("reset")) {
						shapes.clear();
					}
					if(type.get("undo")) {
						redo.add(shapes);
						try {
						shapes=undo.remove(undo.size()-1);
						} catch (IndexOutOfBoundsException iooe) {
							iooe.printStackTrace();
							JOptionPane.showMessageDialog(canvas, "u cannot undo further");
						}
						main.getContentPane().repaint();
					}
					if(type.get("redo")) {
						undo.add(shapes);
						try {
						shapes=redo.remove(undo.size()-1);
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
		
		JPanel x4 = new JPanel();
		x4.setLayout(new BoxLayout(x4, BoxLayout.X_AXIS));
		x4.setPreferredSize(new Dimension(Editor.this.WIDTH,Editor.this.XAXISHEIGHT));
		for(int i=0; i<editable.length; i++) {
			fixed[i].setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			editable[i].setPreferredSize(new Dimension(Editor.this.BUTTONSIZE,Editor.this.XAXISHEIGHT));
			//fixed[i].setEditable(false);
			editable[i].setEditable(true);
			x4.add(fixed[i]);
			x4.add(editable[i]);
		}
		
		for(JPanel p : xAxisPanels) {
			p.setPreferredSize(new Dimension(Editor.this.WIDTH, XAXISHEIGHT));
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
			yAxisContainer.add(p);
		}
		yAxisContainer.add(x4);
		yAxisContainer.add(canvas);
		
		
		main.add(yAxisContainer);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		main.setVisible(true);
		canvas.requestFocus();
		
		undo.add(shapes);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Editor();
	}

}
