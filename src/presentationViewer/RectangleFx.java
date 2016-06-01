package presentationViewer;

/*
 * Author : 
 * Group: 4
 * Description: 
 */

import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
/**
 * This module creates a rectangle and maintains its relative position
 * and height to that of the scene using bindings.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class RectangleFx {
	private double xPos, yPos, width, height;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Rectangle content;
	
	/*Data Fields:
	 * x_pos - (integer between 0 and 1) proportion across the slide the left edge of the rectangle should be
	 * y_pos - (integer between 0 and 1) proportion across the slide the top edge of the rectangle should be
	 * width - (integer between 0 and 1) proportion across the slide the rectangle should span
	 * height- (integer between 0 and 1) proportion down the slide the rectangle should span
	 * lineColour - the colour the outline of the rectangle should be drawn in.
	 * fillColour - the colour the rectangle should be filled with if a flat fill is used.
	 * shading - an object containing the details of the linear fill gradient if one is used. 
	 * isShading - whether a flat fill (false) or linear gradient (true) will be used to fill the shape
	 * content- the Rectangle content that will be placed in the slide
	 * */
	
	/**
	 * The constructor sets all the parameters which will be then read from the XML by the interpreter
	 *
	 * @param x_pos - (integer between 0 and 1) proportion across the slide the left edge of the rectangle should be
	 * @param y_pos - (integer between 0 and 1) proportion across the slide the top edge of the rectangle should be
	 * @param width - (integer between 0 and 1) proportion across the slide the rectangle should span
	 * @param height- (integer between 0 and 1) proportion down the slide the rectangle should span
	 * @param shading - an object containing the details of the linear fill gradient if one is used.
	 * @param lineColour - the colour the outline of the rectangle should be drawn in.
	 * @param fillColour - the colour the rectangle should be filled with if a flat fill is used.
	 * @param isShading - whether a flat fill (false) or linear gradient (true) will be used to fill the rectangle
	 **/
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

	/**This method creates a Rectangle object, using all the parameters specified when
	 * its <tt>constructor</tt> was called, for display within the specified scene.
	 * @param parent - the scene in which this rectangle will be drawn
	 * @return A rectangle object (which is a sub-type of Node)
	 */
	public Node createContent(SubScene parent){
		
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
	
	/**Obtains the content of the node
	 * @return content (node)
	 **/
	public Node getContent(){
		return content;
	}
}


