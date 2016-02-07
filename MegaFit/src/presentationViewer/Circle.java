package presentationViewer;

import java.awt.Color;
import java.awt.geom.*;
import java.awt.*;

class Circle extends Shape {
	
	public Circle(int startTime, int endTime, int startClick,
			int endClick, float x_pos, float y_pos, float width,
			float height, float gx1, float gy1, Color gcolour1,
			float gx2, float gy2, Color gcolour2, Color lineColour,
			String targetLoc) {
		
		super(startTime, endTime, startClick, endClick, x_pos, y_pos,
				width, height, gx1, gx2, gy1, gy2, gcolour1, gcolour2, lineColour, targetLoc);
	}
	
	public void display(Graphics g, int containerW, int containerH) {
		
		Ellipse2D circle = new Ellipse2D.Float((x_pos*containerW)/100, (y_pos*containerH)/100,
				(width*containerW)/100,(height*containerH)/100);
		
		Graphics2D g2d = (Graphics2D) g;
				
	    GradientPaint gradient = new GradientPaint((gx1*containerW)/100, (gy1*containerH)/100,
	    		gcolour1,(gx2*containerW)/100, (gy2*containerH)/100, gcolour2);

	    g2d.setPaint(gradient);
  
		g2d.fill(circle);
		
	    g2d.setPaint(this.lineColour);
		
		g2d.draw(circle);
	}
	
	public boolean contains(int x, int y, int containerW, int containerH) {
		Ellipse2D circle = new Ellipse2D.Float(x_pos*containerW, y_pos*containerH,
				width*containerW,height*containerH);
		
		return circle.contains(x, y);
	}
}
