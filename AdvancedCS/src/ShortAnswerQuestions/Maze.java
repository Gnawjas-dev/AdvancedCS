package ShortAnswerQuestions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
//2d array of maze, true = pass, false = solid/wall
public class Maze {
	private int start = (int)(Math.random()*8+1);
	private int boxwidth=50;
	private int mazesize=10;
	private int endy;
	private boolean[][] maze = new boolean[mazesize][mazesize];
	
	public Maze(){
		makeMaze();
		JFrame frame = new JFrame("graphics");
		frame.setPreferredSize(new Dimension(mazesize*boxwidth, mazesize*boxwidth));
		JPanel canvas = new JPanel() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, boxwidth*mazesize, boxwidth*mazesize);
				for(int i=0; i<maze.length; i++) {
					for(int j=0; j<maze[i].length; j++) {
						if(maze[i][j]) {
							System.out.print("true  ");
							g.setColor(Color.WHITE);
							g.fillRect(j*50, i*50, boxwidth, boxwidth);
						}
						else {
							System.out.print("false ");
						}
					}
					System.out.println();
				}
			}
		};
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
	public void makeMaze() {
		maze = new boolean[10][10];
		maze[start][0] = true;
		//column
		for(int i=1; i<maze.length-1; i++) {
			//row
			for(int j=1; j<maze[i].length-1; j++) {
				if(maze[i-1][j]||maze[i+1][j]||maze[i][j-1]||maze[i][j+1]) {
					if(Math.random()>.5) {
						maze[i][j]=true;
						System.out.println(i + " " + j);
					}
				}
			}
			if(maze[maze.length-1][i]) {
				endy=i;
				break;
			}
		}
		if(!solve(start,0)) {
			makeMaze();
		}	
		print();
	}
	
	public void print() {
		for(int i=0; i<maze.length; i++) {
			for(int j=0; j<maze[i].length; j++) {
				if(maze[i][j])
					System.out.print("true  ");
				else
					System.out.print("false ");
			}
			System.out.println();
		}
	}
	
	public boolean solve (int row, int column) {
      boolean pass = false;
      if (valid(row, column)) {
    	 int pastrow = row, pastcol = column;
    	 System.out.println(row + " " + column);
         if (row == endy && column == maze[endy].length-1)
            pass = true;
         else {
        	if(!pass&&row+1!=pastrow&&column!=pastcol)
        		pass = solve (row+1, column);
            if (!pass&&row!=pastrow&&column+1!=pastcol)
            	pass = solve (row, column+1);
            if (!pass&&row-1!=pastrow&&column!=pastcol)
            	pass = solve (row-1, column);
            if (!pass&&row!=pastrow&&column-1!=pastcol)
            	pass = solve (row, column-1);
         }
         if (pass)  
            maze[row][column] = true;
      }     
      	return pass;
	}
	
	private boolean valid(int row, int column) {
		// TODO Auto-generated method stub
		boolean valid = false;
		if(row>=0&&row<10&&column>=0&&column<10)
			if(maze[row][column])
				valid=true;
		return valid;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Maze();	
	}
	
}