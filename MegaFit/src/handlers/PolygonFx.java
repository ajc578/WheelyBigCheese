package handlers;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: This module creates an javaFX polygon and maintains the relative position 
 * 				of it's points to that of the scene using bindings. The points are retrieved 
 * 				from the target .csv sourceFile.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class PolygonFx extends SlideContent {
	private String sourceFile;
	private Color lineColour, fillColour;
	private ShadingFx shading;
	private boolean isShading;
	private Polygon content;
	private double[] points;
	
	public PolygonFx(int startTime, int duration, String sourceFile, ShadingFx shading, 
			Color lineColour, Color fillColour, Integer targetLoc) {
		
		super(startTime,duration,targetLoc);
		this.sourceFile = sourceFile;
		this.shading = shading;
		this.lineColour = lineColour;
		this.fillColour = fillColour;
		this.isShading = detectGradient();
	}
	
	private boolean detectGradient() {
		boolean gradient = false;
		if (shading != null) {
			gradient = true;
		}
		return gradient;
	}
	
	public Node createContent(Scene parent) {
		
		int count = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("src/res/graphics/" + sourceFile));
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
		
		System.out.println(points.length);
		content = new Polygon();
		for (int j = 0; j < points.length; j+=2) {
			points[j] = points[j]*parent.getWidth();
			points[j+1] = points[j+1]*parent.getHeight();
			content.getPoints().add(j, (Double) points[j]);
			content.getPoints().add(j+1, (Double) points[j+1]);
		}

		/*binding = new ObjectBinding<List<Double>>() {
			{
				super.bind(parent.widthProperty(), parent.heightProperty());
			}
			@Override
			protected List<Double> computeValue() {
				List<Double> list = new ArrayList<Double>();
				double w = parent.getWidth();
				double h = parent.getHeight();
				
				for (int i = 0; i < points.length; i += 2) {
					list.add(points[i]*w);
					list.add(points[i+1]*h);
				}

				return list;
			}
		};
		binding.addListener(new ChangeListener<List<Double>>() {

			@Override
			public void changed(ObservableValue<? extends List<Double>> arg0, List<Double> arg1, List<Double> arg2) {
				// TODO Auto-generated method stub
				content.getPoints().setAll(binding.get());
			}	
		});*/
		
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
	
	private void updatePoints(Scene parent) {
		Double[] newPoints = new Double[points.length];
		for (int i = 0; i < points.length; i+=2) {
			newPoints[i] = points[i] * parent.getWidth();
			newPoints[i+1] = points[i+1] * parent.getHeight();
		}
		content.getPoints().setAll(newPoints);
	}
	
	public Node getContent() {
		return content;
	}
	
	public String getType() {
		return "PolygonFx";
	}
	//Methods associated with Junit tests
	public boolean getDetectGrad() {
		return detectGradient();
	}
	
	public double[] getPoints() {
		return points;
	}
}
