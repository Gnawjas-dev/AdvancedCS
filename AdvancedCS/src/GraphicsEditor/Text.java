package GraphicsEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Text extends Shape{
	private String text;
	private Font f;
	private static FontRenderContext frc= new FontRenderContext(new AffineTransform(), true, true);
	public Text(int x, int y, Font f, Color c, String text) {
		super(x, y, (int)f.getStringBounds(text, frc).getWidth(), (int)f.getStringBounds(text, frc).getHeight(), c);
		
		
		this.f=f;
		this.text=text;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return new Text(x, y, f, c, text);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(c);
		g.setFont(f);
		g.drawString(text, x, y);
		
	}

	@Override
	public boolean isOn(int x, int y) {
		System.out.println(this.x + "  " + this.y + "  " + width + "  " + height);
		// TODO Auto-generated method stud
		return (x>this.x&&x<(this.x+this.width)&&y<this.y&&y>(this.y-height));
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
