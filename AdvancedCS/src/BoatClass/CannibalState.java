package BoatClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import Dijkstra_Algorithm.GPS;

public class CannibalState {
	int MLeft;
	int MRight;
	int CLeft;
	int CRight;
	boolean boatleft = true;
	int limit = 1000000000;
	//true = missionary, false = cannibal
	
	public CannibalState(int mleft, int cleft, int mright, int cright, boolean isleft) {
		MLeft = mleft;
		MRight = mright;
		CLeft = cleft;
		CRight = cright;
		boatleft = isleft;
	}
	
	public ArrayList<CannibalState> solve(int depth, HashSet<CannibalState> previous, ArrayList<CannibalState> sol) {
		
		HashSet<CannibalState> prev = previous;
		ArrayList<CannibalState> solution = sol;
		
		if(isSolved()) {
			solution.add(this);
			return solution;
		}
		if(depth>limit) {
			return null;
		}
		//if illegal
		for(CannibalState cs : nextState()) {
			if(prev.contains(cs)) {
				continue;
			}
			System.out.println(cs);
			prev.add(cs);
			System.out.println("hi");
			ArrayList<CannibalState>x=cs.solve(depth+1, prev, solution);
			if(x!=null) {
				solution.add(this);
				return solution;
			}
		}
		
		return null;
		
	}
	
	public HashSet<CannibalState> nextState(){
//		HashSet<CannibalState> nextState = new HashSet<CannibalState>();
//		
//		int m = 0;
//		if(boatleft) m = Math.min(MLeft, 2);
//		else m = Math.min(MRight, 2);
//		
//		for(int i=0; i<=m ; i++) {
//			int c = 0;
//			if(i==0) c=1;
//			int numCan = 0;
//			if(boatleft) numCan = Math.min(2 - m, CLeft);
//			else numCan = Math.min(2 - m, CRight);
//			for (int j=c; j<=numCan; j++) {
//				CannibalState next = null;
//				if(boatleft) {
//					next = new CannibalState(MLeft-i, CLeft-j, MRight+i, CRight+j, false);
//				} else {
//					next = new CannibalState(MLeft+i, CLeft+j, MRight-i, CRight-j, true);
//				}
//				
//				
//				
//				if(next!=null && next.isLegal()) nextState.add(next);
//			}
//		}
//		return nextState;
		
		HashSet<CannibalState> nexts = new HashSet<CannibalState>();
		for (int nm = 0; nm <= Math.min(2, this.boatleft ? this.MLeft : this.MRight); nm++) {
			for (int nc = (nm==0 ? 1 : 0); nc <= Math.min(2 - nm, this.boatleft ? this.CLeft : this.CRight); nc++) {
				CannibalState next;
				if (boatleft) next = new CannibalState(this.MLeft-nm, this.CLeft - nc, this.MRight + nm, this.CRight + nc, false);
				else next = new CannibalState(this.MLeft+nm, this.CLeft + nc, this.MRight - nm, this.CRight - nc, true);
				if (next.isLegal()) nexts.add(next);
			}
		}
		
		return nexts;
		
	}
	
	public boolean isLegal() {
		
		if(CRight>MRight || CLeft>MLeft) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public boolean isSolved() {
		if(CLeft==0 && MLeft==0) {
			return true;
		}
		else return false;
	}
	
	public String toString() {
		return "Missionaries on the left: " + MLeft + ", Cannibals on the left: " + CLeft + ", \n Missionaries on the right: "
				+ MRight + ", Cannibals on the right: " + MRight;
	}
	
	//hashcode
	
	public int hashCode() {
		String str = "";
		if(boatleft) str+="1";
		else str+="0";
		
		if(CLeft<10) str+="0" + CLeft;
		else str+=CLeft;
		if(MLeft<10) str+="0" + MLeft;
		else str+=MLeft;
		
		if(CRight<10) str+="0" + CRight;
		else str+=CLeft;
		if(MRight<10) str+="0" + MRight;
		else str+=MRight;
		
		return Integer.parseInt(str);
	}
	//equals
	public boolean equals(Object o) {
		CannibalState other = (CannibalState)o;
		if(other.CLeft==this.CLeft && other.CRight==this.CRight && other.MLeft==this.MLeft && other.MRight==this.MRight) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		CannibalState start=new CannibalState(3,3,0,0,true);
		HashSet<CannibalState> prev = new HashSet<>();
		ArrayList<CannibalState> solution = new ArrayList<>();
		prev.add(start);
		System.out.println(start.solve(1, prev, solution));
//		System.out.println(start.nextState());
	}
	
}
