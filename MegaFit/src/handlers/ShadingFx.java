package handlers;

import javafx.scene.paint.Color;

public class ShadingFx {
	
	private double x1,y1,x2,y2;
	private Color colour1,colour2;
	
	public ShadingFx(double x1, double y1, double x2, double y2, Color colour1, Color colour2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.colour1 = colour1;
		this.colour2 = colour2;
	}
	
	public double getX1() {
		return x1;
	}
	
	public double getX2() {
		return x2;
	}
	
	public double getY1() {
		return y1;
	}
	
	public double getY2() {
		return y2;
	}
	
	public void setX1(double x1) {
		this.x1=  x1;
	}
	
	public void setX2(double x2) {
		this.x2=  x2;
	}
	
	public void setY1(double y1) {
		this.y1=  y1;
	}
	
	public void setY2(double y2) {
		this.y2=  y2;
	}
	
	public Color getColour1() {
		return colour1;
	}
	
	public Color getColour2() {
		return colour2;
	}
	
	public void setColour1(Color colour1) {
		this.colour1 = colour1;
	}
	
	public void setColour2(Color colour2) {
		this.colour2 = colour2;
	}
	
}
