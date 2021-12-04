package GraphicsEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Pen extends Shape{
	
	private ArrayList<Shape> penlist = new ArrayList<>();
	private int linewidth;
	
	public Pen(int x, int y, int x2, int y2, int linewidth, Color c) {
		super(x, y, x2, y2, c);
		this.linewidth=linewidth;
		
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void move(int x, int y) {
//		for(int i=0; i<penlist.size();i++) {
//			if(! (penlist.get(i) instanceof Line)) {
//				penlist.get(i).x=x; penlist.get(i).y=y;
//			} else {
//				penlist.remove(i);
//			}
//		} //concurrent modification errors, didn't have time to solve this function (would be easier if line move works)
//			rough draft, a little too complicated to finish last minute
	}
	
	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//basically draw a ton of squares and if there is too big of a gap in between squares then add a line connecting them
		for(int i=1; i<penlist.size(); i++) {
			if(penlist.get(i) instanceof Line || penlist.get(i-1) instanceof Line) {
				continue;
			}
			if(penlist.get(i).x-penlist.get(i-1).x>linewidth)
				penlist.add(i+1, new Line(penlist.get(i-1).x,penlist.get(i-1).y, penlist.get(i).x, 
						penlist.get(i).y, c, linewidth));
		}
		for(Shape r : penlist) {
			r.draw(g);
		}
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		for(Shape s : penlist) {
			if(s.isOn(x, y))
				return true; //doesnt work for line but does for squares
		}
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		penlist.add(new Rectangle(x2,y2,this.linewidth,this.linewidth, c));
	}

}
