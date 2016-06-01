package presentationViewer;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
/**
 * This module creates a javaFX polygon and maintains the relative position 
 * of it's points to that of the scene using bindings. The points are retrieved 
 * from the target .csv sourceFile.
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class PolygonFx extends SlideContent {
	private String sourceFile;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Polygon content;
	private double[] points;
	/*Fields-
	 *sourceFile - the name of the csv file containing the points to draw the polygon with
	 *lineColour - the colour the outline of the circle should be drawn in.
	 *fillColour - the colour the circle should be filled with if a flat fill is used.
	 *shading - an object containing the details of the linear fill gradient if one is used. 
	 *isShading - whether a flat fill (false) or linear gradient (true) will be used to fill the shape
	 *content - the actual ellipse node that will be placed in the slide
	 *points - a list of the points retrieved from the csv*/
	
	/**Constructor simply passes in all the parameters 
	 * (which will be read from the xml by the interpreter)
	 * <p>The Polygon will not actually be created however, until
	 * the <tt>createContent</tt> method is called. 
	 * @param startTime - how many milliseconds into the slide the text should appear at 
	 * @param duration - how many milliseconds the text should last for
	 * @param sourceFile - the name of the csv file containing the points to draw the polygon with
	 * @param an object containing the details of the linear fill gradient if one is used.
	 * @param the colour the outline of the circle should be drawn in.
	 * @param fillColour - the colour the circle should be filled with if a flat fill is used.
	 * @param targetLoc - the slide ID to move to when this object is clicked (or other special value)
	 */
	public PolygonFx(int startTime, int duration, String sourceFile, ShadingFx shading, 
			Color lineColour, Color fillColour, Integer targetLoc) {
		
		super(startTime,duration,targetLoc);
		this.sourceFile = sourceFile;
		this.shading = shading;
		this.lineColour = lineColour;
		this.fillColour = fillColour;
		this.isShading = detectGradient();
	}
	
	/**returns whether or not there is a gradient or flat fill to be used
	 * @return boolean
	 */
	private boolean detectGradient() {
		boolean gradient = false;
		if (shading != null) {
			gradient = true;
		}
		return gradient;
	}
	
	/**This method creates a Polygon object, using all the parameters specified when
	 * its <tt>constructor</tt> was called, for display within the specified scene.
	 * @param parent - the scene in which this polygon will be drawn
	 * @return A Polygon object (which is a sub-type of Node)
	 */
	public Node createContent(SubScene parent) {
		
		int count = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src/res/graphics/" + sourceFile));
			//count how many position values there are in the csv (2 per line for x and y)
			while (br.readLine() != null) {
				count+=2;
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		points = new double[count];
		String line = "";
		int i = 0;
		//fill the points array with the position values
		try {
			br = new BufferedReader(new FileReader("src/res/graphics/" + sourceFile));
			while ((line = br.readLine()) != null) {
				points[i] = Double.parseDouble(line.split(",")[0]);
				i++;
				points[i] = Double.parseDouble(line.split(",")[1]);
				i++;
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		//creat a polygon using these points
		content = new Polygon();
		for (int j = 0; j < points.length; j+=2) {
			points[j] = points[j]*parent.getWidth();
			points[j+1] = points[j+1]*parent.getHeight();
			content.getPoints().add(j, (Double) points[j]);
			content.getPoints().add(j+1, (Double) points[j+1]);
		}
		
		//resize the polygon with the screen
		parent.widthProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				updatePoints(parent);
			}
		});		
		parent.heightProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				updatePoints(parent);
			}
		});
		
		//set the fill and line colouring
		content.setStrokeWidth(2.5);
		content.setStrokeType(StrokeType.INSIDE);
		content.setStroke(lineColour);
		if (isShading) {
			content.setFill(new LinearGradient(shading.getX1(), shading.getY1(), shading.getX2(), shading.getY2(), true, 
					CycleMethod.NO_CYCLE, new Stop(0, shading.getColour1()), new Stop(1, shading.getColour2())));
		} else {
			content.setFill(fillColour);
		}
		
		content.setVisible(false); // need to change to invisible
		
		return content;
	}
	
	/** update the polygons points to resize it with the screen
	 * @param parent
	 */
	private void updatePoints(SubScene parent) {
		Double[] newPoints = new Double[points.length];
		for (int i = 0; i < points.length; i+=2) {
			newPoints[i] = points[i] * parent.getWidth();
			newPoints[i+1] = points[i+1] * parent.getHeight();
		}
		content.getPoints().setAll(newPoints);
	}
	
	/**Return the polygon object generated in <tt>createContent</tt>
	 * @return content - The polygon object
	 */
	public Node getContent() {
		return content;
	}
	
	
	/**Returns the type of SlideElement that this is
	 * @return "PolygonFx"
	 */
	public String getType() {
		return "PolygonFx";
	}
	
	//-------Methods associated with Junit tests------
	public boolean getDetectGrad() {
		return detectGradient();
	}
	
	public double[] getPoints() {
		return points;
	}
}
