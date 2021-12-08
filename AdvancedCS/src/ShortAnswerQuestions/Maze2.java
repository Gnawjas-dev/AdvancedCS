package ShortAnswerQuestions;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Maze2 {
	private int dimension = 8, size = 50, randy = (int)(Math.random()*dimension);
	private Cell[][] cells = new Cell[dimension][dimension];
	private Cell current;
	
	public Maze2() throws InterruptedException {
		
		for(int i=0; i<cells.length; i++) {
			for(int j=0; j<cells[i].length;j++ ) {
				cells[i][j]=new Cell(i,j);
			}
		}
		
		for(int i=0; i<cells.length; i++) {
			for(int j=0; j<cells[i].length;j++ ) {
				cells[i][j].addAdjacent();
			}
		}
		
		JFrame frame = new JFrame("I ATE ANDRIA BAO AHAHA");
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				draw(g);
			}
		};
		
		gen();
		
		System.out.println("done");
		
		frame.setPreferredSize(new Dimension(dimension*size, dimension*size));
		frame.add(canvas);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void draw(Graphics g) {
		//draw background
		g.setColor(Color.white);
		g.fillRect(0, 0, dimension*size, dimension*size);
		
		g.setColor(Color.black);
		for(int i=0; i<cells.length-1; i++) {
			for(int j=0; j<cells[i].length-1;j++ ) {
				cells[i][j].paint(g);
			}
		}
		
	}
	
	public void gen() throws InterruptedException {
		current=cells[0][randy];
		
		int count = 0;
		
		for(Cell c : current.adjacent) {
			System.out.println(randy + " " + c.thisRow + " " + c.thisCol);
		}
		
		while(current.thisCol!=cells.length-1) {
			
			if(count>=dimension*dimension) {
				break;
			}
			
			while(current.unvisitedAdj()) {
				current.visited=true;
				count++;
				Cell next = current.pickRandomAdj();
				
					//wall[		0,	1,	2,		3]
					//		up, right, down, left
				if(next.thisRow>current.thisRow) {
					//next is below current
					next.walls[0]=false;
					current.walls[2]=false;
				}
				if(next.thisRow<current.thisRow) {
					//next is above current
					next.walls[2]=false;
					current.walls[0]=false;
				}
				if(next.thisCol>current.thisCol) {
					//next is right of current
					next.walls[3]=false;
					current.walls[1]=false;
				}
				if(next.thisCol<current.thisCol) {
					//next is left of current
					next.walls[1]=false;
					current.walls[3]=false;
				}
				
				next.previous=current;
				current = next;
				
				Thread.sleep(1);
				
			}
			while(!current.unvisitedAdj()) {
				current=current.previous;
			}
			
			
			
		}
	}
	
	public class Cell {
		
		private int x, y, thisRow, thisCol;
		private boolean visited = false;
		private Cell previous;
		private boolean walls[] = {true, true, true, true}; //up, right, down, left
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
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		new Maze2();
	}

}
