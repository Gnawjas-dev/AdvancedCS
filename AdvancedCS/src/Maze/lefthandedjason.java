package Maze;

import java.awt.Color;

public class lefthandedjason extends Bot{
	
	//uses left hand rule
	//doesnt work if there are patches of open box
	
	boolean one = true, two = false, three = false;
	int step = 0;
	
	public lefthandedjason(MazeRunner mr, Color c) {
		super(mr, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		//3 phases
		//1: always try to move forward
			//if success: go to 2
			//if fail:	  go to 3
		//2: turn left, go to 1
		//3: turn right, go to 1
		
		if(one) {
			if(moveForward()) {
				two=true;
				one=false;
				three=false;
			}
			else {
				three=true;
				two=false;
				one=false;
			}
		}
		else if(two) {
			turnLeft();
			two=false;
			three=false;
			one=true;
		}
		
		else if(three) {
			if(step!=3) {
				turnLeft();
				step++;
			}
			else {
				step=0;
				one=true;
				two=false;
				three=false;
			}
		}
		
	}

}
