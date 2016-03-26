package handlers;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module determines what shape handler to call based on the
 * 				string 'shapeType'. Also it detects if a valid gradient has been 
 * 				passed to its constructor.
 */

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class ShapeFx extends SlideContent {
	
	private double xStart, yStart, width, height;
	private String shapeType;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private Node content;
	
	public ShapeFx(int startTime, int duration, double xStart, double yStart, 
				   double width, double height, String shapeType, Color lineColour, 
				   Color fillColour, ShadingFx shading, Integer targetLoc) {
		super(startTime, duration,targetLoc);
		this.xStart = xStart;
		this.yStart = yStart;
		this.width = width;
		this.height = height;
		this.shapeType = shapeType;
		this.lineColour = lineColour;
		this.fillColour = fillColour;
		this.shading = shading;
	}
	
	private boolean detectGradient() {
		boolean gradient = false;
		if (shading != null) {
			gradient = true;
		}
		return gradient;
	}
	
	public Node createContent(Scene scene) {
		if (shapeType.matches("circle")) {
			CircleFx circle = new CircleFx(xStart,yStart,width,height,shading,lineColour,fillColour,detectGradient());
			content = circle.createContent(scene);
		} else if (shapeType.matches("rectangle")) {
			RectangleFx rectangle = new RectangleFx(xStart,yStart,width,height,shading,lineColour,fillColour,detectGradient());
			content = rectangle.createContent(scene);
		} else if (shapeType.matches("rounded rectangle")) {
			RoundedRectangleFx roundedRectangle = new RoundedRectangleFx(xStart,yStart,width,height,shading,lineColour,fillColour,detectGradient());
			content = roundedRectangle.createContent(scene);
		} else {
			content = null;
		}
		return content;
	}
	
	public Node getContent() {
		return content;
	}
	
	public String getType() {
		return "ShapeFx";
	}
	
	public boolean getDetectGrad() {
		return detectGradient();
	}
	
}
