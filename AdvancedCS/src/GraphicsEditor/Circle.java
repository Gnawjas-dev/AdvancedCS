package GraphicsEditor;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape{
	
	public Circle(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return new Circle(x,y,width,height, c);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(c);
		g.fillOval(x, y, width, height);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		//radius
		//(int) Math.sqrt(Math.pow(endX-startX, 2)+Math.pow(endY-startY, 2)); for circle
		//x^2/centerx + y^2/centery=1 for ellipses
		//area when <= 1
		int centerx = width/2+this.x;
		int centery = height/2+this.y;
		double hradius =  Math.pow(height/2,2);
		double wradius =  Math.pow(width/2,2);
//		System.out.println(x + " " + centerx + " " + this.x + " " + wradius);
		return((Math.pow(x-centerx, 2)/wradius)+(Math.pow(y-centery, 2)/hradius)<=1);
		
//		int d = (int) Math.sqrt(Math.pow(x-centerx, 2)+Math.pow(y-centery, 2));
//		
//		return (width/2 >= d);
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		width = Math.abs(x2-x1);
		height = Math.abs(y2-y1);
		x=Math.min(x2, x1);
		y=Math.min(y1, y2);
	}

}
