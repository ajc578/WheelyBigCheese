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

	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before
	public void setUp() {
		scene = new SubScene(layout,800,600);
		text = new TextFx(0, 0, 0.1, 0.8, "<b>Hello!</b> This <i>is</i> a <b><i>test</i></b>.",
				"Calibri", 12, Color.AQUAMARINE, 0);
		textFlow = (TextFlow) text.createContent(scene);
		System.out.println(text.getNumOfStyleChanges());
		layout.getChildren().add(textFlow);
	}

	@Test
	public void testPosition() {
		assertNotNull(textFlow);
		assertEquals(textFlow.getLayoutX(),80.0,0);
		assertEquals(textFlow.getLayoutY(), 480.0,0);
	}

	@Test
	public void testContent() {
		assertEquals(text.getText(),"Hello! This is a test.");
		assertEquals(text.getNumOfStyleChanges(),6,0);
	}

	@Test
	public void testFont() {
		assertEquals(text.getFontSize(),12);
		assertEquals(text.getFont(), "Calibri");
		assertEquals(text.getFontColour(), Color.AQUAMARINE);
	}

}
