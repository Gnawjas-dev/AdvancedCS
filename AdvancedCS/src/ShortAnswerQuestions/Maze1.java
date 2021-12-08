package ShortAnswerQuestions;

import java.awt.Color;

import java.awt.Dimension;

import java.awt.Graphics;

import java.util.ArrayList;

import javax.swing.JFrame;

import javax.swing.JPanel;

public class Maze1 {
	
	private int dimension=10;
	
	private int size = 50;
	
	private Cell[][] cells = new Cell[dimension][dimension];
	
	private Cell current;
	
	private boolean finished;
	
	public class Cell {
		
		private int x, y, thisRow, thisCol;
		
		private boolean visited = false;
		
		private boolean walls[] = {true, true, true, true};
		
		private ArrayList<Cell> adjacent = new ArrayList<>();
		
		public Cell(int row, int col) {
			
			x=row*size;
			
			y=col*size;
			
			thisRow=row;
			
			thisCol=col;
			
		}
		
		public boolean unvisitedAdj() {
			
			for(Cell c : adjacent) {
				
				if(!c.visited)
					
					return true;
			}
			
			return false;
			
		}
		
		public Cell pickRandomAdj() {
			
			Cell adj = adjacent.get((int)(Math.random()*adjacent.size()));
			
			while(adj.visited) {
				
				adjacent.remove(adj);
				
				adj=adjacent.get((int)(Math.random()*adjacent.size()));
				
			}
			
			adj.visited=true;
			
			adjacent.remove(adj);
			
			return adj;
			
		}
		
		public void paint(Graphics g) {
			
			g.setColor(Color.BLACK);
			
			if(walls[0])
				
				g.drawLine(x, y, x+size, y);
			
			if(walls[1])
				
				g.drawLine(x+size, y, x+size, y+size);
			
			if(walls[2])
				
				g.drawLine(x+size, y+size, x, y+size);
			
			if(walls[3])
				
				g.drawLine(x, y+size, x, y);
			
			if(visited) {
				
				g.setColor(Color.PINK);
				
				g.fillRect(x+5, y+5, thisRow-10, thisCol-10);
				
			}
			
		}
		
		public void addAdjacent() {
			
			if(thisRow>0) {
				
				adjacent.add(cells[thisRow-1][thisCol]);
				
			}
			
			if(thisRow<dimension-1) {
				
				adjacent.add(cells[thisRow+1][thisCol]);
				
			}
			
			if(thisCol>0) {
				
				adjacent.add(cells[thisRow][thisCol-1]);
				
			}
			
			if(thisCol<dimension-1) {
				
				adjacent.add(cells[thisRow][thisCol+1]);
				
			}
			
		}
		
	}
	
	public Maze1() {
		
		for(int i=0; i<dimension; i++) {
			
			for(int j=0; j<dimension; j++) {
				
				cells[i][j]=new Cell(i,j);
				
			}
			
		}
		
		for(int i=0; i<dimension; i++) {
			
			for(int j=0; j<dimension; j++) {
				
				cells[i][j].addAdjacent();
				
			}
			
		}
		
		current = cells[0][0];
		
		current.visited = true;
		
		JFrame frame = new JFrame("MazeGame");
		
		JPanel canvas = new JPanel() {
			
			public void paint(Graphics g) {
				
				super.paint(g);
				
				draw(g);
				
			}
			
		};
		
		frame.setPreferredSize(new Dimension(dimension*size,dimension*size));
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		frame.add(canvas);
		
		frame.pack();
		
		frame.setVisible(true);
	
	}
	
	public void draw(Graphics g) {
		
		
	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		new Maze1();
	
	}

}
