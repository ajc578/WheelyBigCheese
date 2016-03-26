package handlers;

/*
 * Author: Alexander Chapman
 * Group: 4
 * Description: This module creates a circle and maintains its relative position
 * 				and height to that of the scene using bindings.
 */

import javafx.scene.shape.*;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.*;

public class CircleFx  {
	private double xPos, yPos, width, height;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Ellipse content;
	
	
	public CircleFx(double xPos, double yPos, double width, double height, 
					ShadingFx shading, Color lineColour, Color fillColour, boolean isShading){
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.shading = shading;
		this.lineColour = lineColour;
		this.fillColour = fillColour;
		this.isShading = isShading;
	}

	public Node createContent(Scene parent){
		
		NumberBinding radXDyn = (parent.widthProperty().multiply(width)).divide(2);
		NumberBinding radYDyn = (parent.heightProperty().multiply(height)).divide(2);
		NumberBinding x_centerDyn = (parent.widthProperty().multiply(xPos)).add(radXDyn);
		NumberBinding y_centerDyn = (parent.heightProperty().multiply(yPos)).add(radYDyn);

		
		content = new Ellipse();
		content.radiusXProperty().bind(radXDyn);
		content.radiusYProperty().bind(radYDyn);
		content.centerXProperty().bind(x_centerDyn);
		content.centerYProperty().bind(y_centerDyn);
		
		content.setStrokeType(StrokeType.INSIDE);
		content.setStroke(lineColour);
		if (isShading) {
			content.setFill(new LinearGradient(shading.getX1(), shading.getY1(), shading.getX2(), shading.getY2(), true, 
					CycleMethod.NO_CYCLE, new Stop(0, shading.getColour1()), new Stop(1, shading.getColour2())));
		} else {
			content.setFill(fillColour);
		}
		content.setVisible(false);
		return content;
	}

}
