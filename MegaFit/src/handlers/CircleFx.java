package handlers;

import javafx.scene.shape.*;
import javafx.beans.binding.NumberBinding;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.*;
/**
 * A class to create ellipse shapes for use in building and 
 * displaying presentations, it is locked t the size of the scene
 * using bindings, and has internal shading
 * <p> <STRONG> Developed by </STRONG> <p>
 * Alexander Chapman
 * <p> <STRONG> Tested by </STRONG> <p>
 * Alexander Chapman and Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Alexander Chapman
 */
public class CircleFx{
	private double xPos, yPos, width, height;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Ellipse content;
	/*Fields-
	 *x_Pos - (integer between 0 and 1) proportion across the slide the left edge of the shape should be
	 *y_Pos - (integer between 0 and 1) proportion down the slide the top edge of the shape should be
	 *width - (integer between 0 and 1) proportion across the slide the shape should span
	 *height - (integer between 0 and 1) proportion down the slide the shape should span
	 *lineColour - the colour the outline of the circle should be drawn in.
	 *fillColour - the colour the circle should be filled with if a flat fill is used.
	 *shading - an object containing the details of the linear fill gradient if one is used. 
	 *isShading - whether a flat fill (false) or linear gradient (true) will be used to fill the shape
	 *content - the actual ellipse node that will be placed in the slide*/
	
	/**
	 * Constructor simply passes in all the parameters 
	 * (which will be read from the xml by the interpreter)
	 * <p>The Ellipse will not actually be created however, until
	 * the <tt>createNode</tt> method is called. 
	 * 
	 *@param x_Pos - (integer between 0 and 1) proportion across the slide the left edge of the shape should be
	 *@param y_Pos - (integer between 0 and 1) proportion down the slide the top edge of the shape should be
	 *@param width - (integer between 0 and 1) proportion across the slide the shape should span
	 *@param height - (integer between 0 and 1) proportion down the slide the shape should span
	 *@param lineColour - the colour the outline of the circle should be drawn in.
	 *@param fillColour - the colour the circle should be filled with if a flat fill is used.
	 *@param shading - an object containing the details of the linear fill gradient if one is used. 
	 *@param isShading - whether a flat fill (false) or linear gradient (true) will be used to fill the shape
	 *@param content - the actual ellipse node that will be placed in the slide
	 */
	public CircleFx(double xPos, double yPos, double width, double height, 
					ShadingFx shading, Color lineColour, Color fillColour, boolean isShading){
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.shading = shading;
		this.lineColour = lineColour;
		this.fillColour = fillColour;
		this.isShading = isShading;
	}

	/**This method creates a Ellipse object, using all the parameters specified when
	 * its <tt>constructor</tt> was called, for display within the specified scene.
	 * @param parent - the scene in which this ellipse will be drawn
	 * @return An ellipse object (which is a sub-type of Node)
	 */
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
