package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test RoundedRectangleFx
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import presentationViewer.RoundedRectangleFx;
import presentationViewer.ShadingFx;

/**	The following test class tests if the RoundedRectangleFX class can
 *  successfully create an rounded rectangle object
 * **/
public class TestRoundedRectangleFx {

	Group layout = new Group();
	RoundedRectangleFx roundedrectanglefx;
	ShadingFx shading;
	LinearGradient grad;
	Rectangle rectangle;
	SubScene scene;
	/*Data Fields-
	 * layout - test pane that is "placed" in the scene
	 * roundedrectanglefx - RoundedRectangleFx test object
	 * shading - an object containing the details of the linear fill gradient if one is used. 
	 * grad - test gradient of the test rounded rectangle
	 * rectangle - the test rectangle node that is placed in the test slide
	 * scene - container for all content in the test scene graph.
	 * **/
	
	// This rule allows us to run tests for JavaFX based classes
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	
	/**The following method sets up the testing framework. 
	 * It creates a RectangleFx object and places it inside
	 * a layout.
	 * **/
	@Before
	public void setUp() {
		scene = new SubScene(layout,800,600);
		shading = new ShadingFx(0.0, 0.0, 1.0, 1.0, Color.BLUEVIOLET, Color.ALICEBLUE);
		roundedrectanglefx = new RoundedRectangleFx(0.2, 0.24, 0.5, 0.5, shading, Color.GOLD, Color.GRAY, true);
		rectangle = (Rectangle) roundedrectanglefx.createContent(scene);
		grad = new LinearGradient(shading.getX1(), shading.getY1(), shading.getX2(), shading.getY2(), true,
				CycleMethod.NO_CYCLE, new Stop(0, shading.getColour1()), new Stop(1, shading.getColour2()));
		layout.getChildren().add(rectangle);
	}

	/** 
	 * The following method asserts the position and dimension
	 * properties of the test RoundedRectangleFx object
	 **/
	@Test
	public void testPositionAndDim() {
		assertEquals(rectangle.getLayoutX(),160.0,0);
		assertEquals(rectangle.getLayoutY(), 144.0,0);
		assertEquals(rectangle.getWidth(), 400.0,0);
		assertEquals(rectangle.getHeight(), 300.0,0);
	}

	/** 
	 * The following method asserts the arc dimensions
	 * properties of the test RoundedRectangleFx object
	 **/
	@Test
	public void testArcs() {
		assertEquals(rectangle.getArcWidth(),40.0,0);
		assertEquals(rectangle.getArcHeight(),40.0,0);
	}

	/**
	 * The following method asserts the successful formation of the test object
	 **/
	@Test
	public void testContent() {
		assertNotNull(rectangle);
	}

	/** 
	 * The following method asserts the colour
	 * properties of the test RoundedRectangleFx object
	 **/
	@Test
	public void testColours() {
		assertEquals(rectangle.getFill(), grad);
		assertEquals(rectangle.getStroke(), Color.GOLD);
	}

}


