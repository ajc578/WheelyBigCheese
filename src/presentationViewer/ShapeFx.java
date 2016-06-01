package presentationViewer;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
/**
 * This module determines what shape handler to call based on the
 * string 'shapeType'. Also it detects if a valid gradient has been 
 * passed to its constructor.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class ShapeFx extends SlideContent {
	
	private double xStart, yStart, width, height;
	private String shapeType;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private Node content;
	
	/*Data Fields:
	 * xStart - X coordinate of the axis start point
	 * yStart - Y coordinate of the axis start point
	 * width - (integer between 0 and 1) proportion across the slide the shape should span
	 * height- (integer between 0 and 1) proportion down the slide the shape should span
	 * shapeType - the type of the shape (i.e. circle or rectangle or rounded rectangle)
	 * lineColour - the colour the outline of the shape should be drawn in.
	 * fillColour - the colour the shape should be filled with if a flat fill is used.
	 * shading - an object containing the details of the linear fill gradient if one is used. 
	 * content- the Shape content that will be placed in the slide
	 * */
	
	/**
	 * The constructor sets all the parameters which will be then read from the XML by the interpreter
	 *
	 * @param startTime - the time in milliseconds into the slide before an element will present itself
	 * @param duration - the time in milliseconds that the slide will last
	 * @param xStart - X coordinate of the axis start point
	 * @param yStart - Y coordinate of the axis start point
	 * @param width - (integer between 0 and 1) proportion across the slide the shape should span
	 * @param height- (integer between 0 and 1) proportion down the slide the shape should span
	 * @param shapeType - the type of the shape (i.e. circle or rectangle or rounded rectangle)
	 * @param lineColour - the colour the outline of the shape should be drawn in.
	 * @param fillColour - the colour the shape should be filled with if a flat fill is used.
	 * @param shading - an object containing the details of the linear fill gradient if one is used.
	 * @param isShading - whether a flat fill (false) or linear gradient (true) will be used to fill the shape
	 **/
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
	
	/**
	 * This method detects if the shape is shaded
	 * @return the presence of the gradient 
	 * **/
	private boolean detectGradient() {
		boolean gradient = false;
		if (shading != null) {
			gradient = true;
		}
		return gradient;
	}
	
	/** This method creates an object according to the shape type.
	 * 
	 * @param parent - the scene in which this shape will be drawn
	 * @return A circle, rectangle or rounded rectangle object (which is a sub-type of Node)
	 */
	public Node createContent(SubScene scene) {
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
		
	/**Obtains the content of the node
	 * @return content (node)
	 **/
	public Node getContent() {
		return content;
	}
	
	/**Obtains the type of the shape
	 * @return type of shape (String)
	 **/
	public String getType() {
		return shapeType;
	}
	
	/**Detects the presence of the gradient
	 * @return the presence of gradient (node)
	 **/
	public boolean getDetectGrad() {
		return detectGradient();
	}
	
}


