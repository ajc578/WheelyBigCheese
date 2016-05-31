package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test TextFx
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import presentationViewer.TextFx;

public class TestTextFx {

	Group layout = new Group();
	TextFx text;
	SubScene scene;
	TextFlow textFlow;
	
	/*Data Fields:
	 * layout - test pane that is "placed" in the scene
	 * text - TextFx test object
	 * scene - container for all content in the test scene graph.
	 * text flow - TextFlow test layout
	 */
	
	// This rule allows us to run tests for JavaFX based classes
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	/**The following method sets up the testing framework. 
	 * It creates a TextFx object and places it inside
	 * a layout.
	 * **/
	@Before
	public void setUp() {
		scene = new SubScene(layout,800,600);
		text = new TextFx(0, 0, 0.1, 0.8, 0.3, 0.2, "<b>Hello!</b> This <i>is</i> a <b><i>test</i></b>.",
				"Calibri", 12, Color.AQUAMARINE, 0);
		textFlow = (TextFlow) text.createContent(scene);
		System.out.println(text.getNumOfStyleChanges());
		layout.getChildren().add(textFlow);
	}

	/** 
	 * The following method asserts the position properties 
	 * of the test TestFx object
	 **/
	@Test
	public void testPosition() {
		assertNotNull(textFlow);
		assertEquals(textFlow.getLayoutX(),80.0,0);
		assertEquals(textFlow.getLayoutY(), 480.0,0);
	}

	/**
	 * The following method asserts the successful formation of the test object
	 **/
	@Test
	public void testContent() {
		assertEquals(text.getText(),"Hello! This is a test.");
		assertEquals(text.getNumOfStyleChanges(),6,0);
	}

	/** 
	 * The following method asserts the text properties 
	 * of the test TestFx object
	 **/
	@Test
	public void testFont() {
		assertEquals(text.getFontSize(),12);
		assertEquals(text.getFont(), "Calibri");
		assertEquals(text.getFontColour(), Color.AQUAMARINE);
	}

}
