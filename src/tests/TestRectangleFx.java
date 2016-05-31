package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test ImageFx
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import presentationViewer.ImageFx;

	/**	The following test class tests if the ImageFX class can
	 *  successfully create an imageView object
	 * **/
public class TestImageFx {

	Group layout = new Group();
	ImageFx image;
	SubScene scene;
	ImageView imageView;
	/*Data Fields-
	 *layout - test pane that is "placed" in the scene
	 *image - ImageFx test object
	 *scene - container for all content in the test scene graph.
	 *imageView - imageView test object
	 * **/
	

	// This rule allows us to run tests for JavaFX based classes
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	/**The following method sets up the testing framework. 
	 * It creates an ImageFx object and places it inside
	 * a layout.
	 * **/
	@Before
	public void setUp() {
		scene = new SubScene(layout,800,600);
		image = new ImageFx(0,0,0.1,0.2,0.4,0.4,"play.png",0);
		imageView = (ImageView) image.createContent(scene);
		layout.getChildren().add(imageView);
	}

	/** 
	 * The following method asserts the properties of the test imageFx object
	 **/
	@Test
	public void testPositionAndDim() {
		assertEquals(imageView.getX(),80.0,0);
		assertEquals(imageView.getY(), 120.0,0);
		assertEquals(imageView.getFitWidth(), 320.0,0);
		assertEquals(imageView.getFitHeight(), 240.0,0);
	}

	/**
	 * The following method asserts the successful formation of the test object
	 **/
	@Test
	public void testContent() {
		assertNotNull(imageView);
	}


}


