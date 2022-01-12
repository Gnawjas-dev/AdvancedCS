package Maze;

import java.awt.Color;
import java.util.ArrayList;

public class scrap extends Bot{
	
	ArrayList<Boolean>actions = new ArrayList<>(); //true = move forward, false == move backward
	
	boolean normal = true; int step = 0;
	boolean backtrack = false; boolean checkopen = false;
	
	public scrap(MazeRunner mr, Color c) {
		super(mr, c);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		if(normal) {
			
			//normal movement: always try to go forward, if fails, check left and right
			//stops at dead end, backtrack time
			
			if(step==0) {
				if(!moveForward()) {
					step++;
				} else actions.add(true);
			}
			
			if(step==1) {
				turnLeft();
				actions.add(false);
				step++;
			}
			if(step==2) {
				if(!moveForward()) {
					step++;
				}
				else {
					actions.add(true);
					step=0;
				}
			}
			if(step>2&&step<5) {
				turnLeft();
				actions.add(false);
				step++;
			}
			if(step==5) {
				if(!moveForward()) {
					//turn around and then change state
					step++;
				}
				else {
					actions.add(true);
					step=0;
				}
			}
			//turn around, dont record
			if(step>5&&step<8) {
				turnLeft();
				step++;
			}
			
			if(step==8) {
				backtrack=true;
				normal=false;
				step=0;
			}
		}
		
		if(backtrack) {
			if(!checkopen) {
				
				if(!actions.get(actions.size()-1)&&!actions.get(actions.size()-2)&&!actions.get(actions.size()-3)) {
					actions.remove(actions.size()-1);
					actions.remove(actions.size()-2);
					actions.remove(actions.size()-3);
					turnLeft();
				}
				
				if(actions.get(actions.size()-1)) {
					moveForward();
					
					checkopen=true;
				} else {
					if(step<3) {
						turnLeft();
						step++;
					}
					else {
						actions.remove(actions.size()-1);
						step=0;
					}
				}
			}
			if(checkopen) {
				//since it's already always either went forward left, or right we can only check when its on move forward
				if(actions.remove(actions.size()-1)) {
					
				}
			}
		}
		
	}

}
