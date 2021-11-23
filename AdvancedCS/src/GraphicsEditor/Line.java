package GraphicsEditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line extends Shape{
	private int linewidth;
	
	//width and height are x2 and y2 respectively here
	
	public Line(int x, int y, int w, int h, Color c, int linewidth) {
		super(x, y, w, h, c);
		this.linewidth = linewidth;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return new Line(x+50,y+50,width,height, c, linewidth);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(c);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(linewidth));
		g.drawLine(x, y, width, height);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		width = x2;
		height = y2;
	}

}
