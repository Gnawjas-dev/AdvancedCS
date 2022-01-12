package Maze;

import java.awt.Color;
import java.util.ArrayList;

public class RandomBot extends Bot{
	boolean turning = false;
	boolean move = true;
	boolean left, right, up, down;
	int cnt=0;
	ArrayList<Boolean> list = new ArrayList<>();
	
	public RandomBot(MazeRunner mr, Color c) {
		super(mr, c);
		// TODO Auto-generated constructor stub
		
	}
	

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		
		if(move) {
			if(!moveForward()) {
				turning=true;
				move=false;
				return;
			} else {
				list.add(true); //true = move forward, false = turn left
			}
		}
		
		if(turning) {
			cnt=0;
			turnLeft();
			list.add(false);
			turning=false;
			move=true;
			return;
		}
		
		
		
		
	}

}
