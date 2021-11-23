package GraphicsEditor;
import java.awt.Color;
import java.awt.Graphics;
public abstract class Shape {
protected int x, y, width, height;
protected Color c;
protected String type;
public Shape(int x,int y, int x2, int y2, Color c) {
		this.x = x; 
		this.y = y;
		width = x2; 
		height = y2;
		this.c = c;
	}
		public void move(int x1, int y1) {
				x = x1; y = y1;
		}
	public abstract Shape copy();
	public abstract void draw(Graphics g);
	public abstract boolean isOn(int x, int y);
	public abstract void resize(int x1, int y1, int x2, int y2);
}