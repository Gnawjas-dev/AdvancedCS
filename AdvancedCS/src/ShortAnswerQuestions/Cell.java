package ShortAnswerQuestions;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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