package BoatClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class CannibalsState {
	
	int numMissionariesLeft, numCannibalsLeft, numMissionariesRight, numCannibalsRight;
	boolean boatOnLeft;
	int limit = 1000000000;
	
	public CannibalsState(int nmL, int ncL, int nmR, int ncR, boolean left) {
		numMissionariesLeft = nmL;
		numCannibalsLeft = ncL;
		numMissionariesRight = nmR;
		numCannibalsRight = ncR;
		this.boatOnLeft = left;
	}
	
	
	public boolean isLegal() {
		if (!(this.numCannibalsLeft >= 0 && this.numMissionariesLeft >= 0 && this.numCannibalsRight >= 0 && this.numMissionariesRight >= 0)) return false;
		if (this.numCannibalsLeft > this.numMissionariesLeft && this.numMissionariesLeft > 0) return false;
		if (this.numCannibalsRight > this.numMissionariesRight && this.numMissionariesRight > 0) return false;
		return true;
	}
	
	public boolean isSolved() {
		return numMissionariesLeft == 0 && numCannibalsLeft == 0;
	}

	public boolean equals(Object o) {
		CannibalsState other = (CannibalsState)o;
		return other.numCannibalsLeft == this.numCannibalsLeft && other.numCannibalsRight == this.numCannibalsRight &&
				other.numMissionariesLeft == this.numMissionariesLeft && other.numMissionariesRight == this.numMissionariesRight;
		
	}
	
	public int hashCode() {
		return 10*this.numMissionariesLeft + 1000*this.numCannibalsLeft + 100000*this.numMissionariesRight + 10000000*this.numCannibalsRight + (boatOnLeft ? 1:0);
	}
	
	public String toString() {
		return numMissionariesLeft + "/" + numCannibalsLeft + " || " + numMissionariesRight + "/" + numCannibalsRight + "\n";
	}
	
	public HashSet<CannibalsState> nextStates() {
		HashSet<CannibalsState> nexts = new HashSet<CannibalsState>();
		
		for (int nm = 0; nm <= Math.min(2, boatOnLeft ? this.numMissionariesLeft : this.numMissionariesRight); nm++) {
			for (int nc = (nm==0 ? 1 : 0); nc <= Math.min(2 - nm, boatOnLeft ? this.numCannibalsLeft : this.numCannibalsRight); nc++) {
			 
				CannibalsState next;
				if (boatOnLeft)
					next = new CannibalsState(this.numMissionariesLeft-nm, this.numCannibalsLeft - nc, this.numMissionariesRight + nm, this.numCannibalsRight + nc, false);
				else
					next = new CannibalsState(this.numMissionariesLeft+nm, this.numCannibalsLeft + nc, this.numMissionariesRight - nm, this.numCannibalsRight - nc, true);
				if (next.isLegal())
					nexts.add(next);
			}
		}
		return nexts;	
	}
	
	public ArrayList<CannibalsState> solve(int depth, HashSet<CannibalsState> previous, ArrayList<CannibalsState> sol) {
		
		HashSet<CannibalsState> prev = previous;
		ArrayList<CannibalsState> solution = sol;
		
		if(isSolved()) {
			solution.add(this);
			return solution;
		}
		if(depth>limit) {
			return null;
		}
		//if illegal
		for(CannibalsState cs : nextStates()) {
			if(prev.contains(cs)) {
				continue;
			}
			System.out.println(cs);
			prev.add(cs);
			ArrayList<CannibalsState>x=cs.solve(depth+1, prev, solution);
			if(x!=null) {
				solution.add(this);
				return solution;
			}
		}
		
		return null;
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CannibalsState start=new CannibalsState(3,3,0,0,true);
		HashSet<CannibalsState> prev = new HashSet<>();
		ArrayList<CannibalsState> solution = new ArrayList<>();
		prev.add(start);
		System.out.println(start.solve(1, prev, solution));
//		System.out.println(start.nextStates());
	}
		
}
