package GraphicsEditor;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape{

	public Rectangle(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
		//width is x2
		
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return new Rectangle(x+50,y+50,width,height,c);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return (x>this.x&&x<(this.x+width)&&y>this.y&&y<(this.y+height));
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		width = Math.abs(x2-x1);
		height = Math.abs(y2-y1);
		x=Math.min(x2, x1);
		y=Math.min(y1, y2);
	}

}
