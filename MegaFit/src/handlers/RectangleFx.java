package handlers;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module creates a rectangle and maintains its relative position
 * 				and height to that of the scene using bindings.
 */

import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class RectangleFx {
	private double xPos, yPos, width, height;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Rectangle content;
	
	
	public RectangleFx(double xPos, double yPos, double width,
			double height, ShadingFx shading, Color lineColour, Color fillColour, boolean isShading){
		
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
		
		NumberBinding widthBinding = parent.widthProperty().multiply(width);
		NumberBinding heightBinding = parent.heightProperty().multiply(height);
		NumberBinding xPosBinding = parent.widthProperty().multiply(xPos);
		NumberBinding yPosBinding = parent.heightProperty().multiply(yPos);

		
		content = new Rectangle();
		content.layoutXProperty().bind(xPosBinding);
		content.layoutYProperty().bind(yPosBinding);
		content.widthProperty().bind(widthBinding);
		content.heightProperty().bind(heightBinding);
		
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
	
	public Node getContent(){
		return content;
	}
}
