package presentationViewer;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module has getter and setter methods for adjusting/storing attributes
 * 				of a linear gradient between two colours.
 */

import javafx.scene.paint.Color;
/**
 *This module has getter and setter methods for adjusting/storing attributes
 *of a linear gradient between two colours.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class ShadingFx {
	
	private double x1,y1,x2,y2;
	private Color colour1,colour2;
	
	/*Data Fields:
	 * x1 - X coordinate of the gradient axis start point
	 * y1 - Y coordinate of the gradient axis start point
	 * x2 - X coordinate of the gradient axis end point
	 * y2 - Y coordinate of the gradient axis end point
	 * colour1 - Starting colour
	 * colour2 - Final colour
	 * */
	
	/**
	 * The constructor sets all the parameters which will be read from the XML by the interpreter
	 * 
	 * @param x1 - X coordinate of the gradient axis start point
	 * @param y1 - Y coordinate of the gradient axis start point
	 * @param x2 - X coordinate of the gradient axis end point
	 * @param y2 - Y coordinate of the gradient axis end point
	 * @param colour1 - Starting colour
	 * @param colour2 - Final colour
	 **/
	public ShadingFx(double x1, double y1, double x2, double y2, Color colour1, Color colour2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.colour1 = colour1;
		this.colour2 = colour2;
	}
	
	// get/set methods for all the data fields of the class
	
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


