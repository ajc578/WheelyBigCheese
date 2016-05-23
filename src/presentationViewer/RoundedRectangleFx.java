package presentationViewer;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module creates a rounded rectangle and maintains its relative position
 * 				and height to that of the scene using bindings.
 */

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class RoundedRectangleFx {
	private double xPos, yPos, width, height;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Rectangle content;
	
	
	public RoundedRectangleFx(double xPos, double yPos, double width,
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

	public Node createContent(SubScene parent){
		
		content = new Rectangle();
		
		NumberBinding widthBinding = parent.widthProperty().multiply(width);
		NumberBinding heightBinding = parent.heightProperty().multiply(height);
		NumberBinding xPosBinding = parent.widthProperty().multiply(xPos);
		NumberBinding yPosBinding = parent.heightProperty().multiply(yPos);
		NumberBinding arcBinding = Bindings.max(content.widthProperty(), content.heightProperty()).multiply(0.1);
		
		content.layoutXProperty().bind(xPosBinding);
		content.layoutYProperty().bind(yPosBinding);
		content.widthProperty().bind(widthBinding);
		content.heightProperty().bind(heightBinding);
		content.arcWidthProperty().bind(arcBinding);
		content.arcHeightProperty().bind(arcBinding);
		
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
