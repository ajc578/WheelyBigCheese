package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test CircleFx
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import handlers.CircleFx;
import handlers.ShadingFx;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;

public class TestCircleFx {

	Group layout = new Group();
	CircleFx circlefx;
	ShadingFx shading;
	LinearGradient grad;
	Ellipse circle;
	Scene scene;
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() {
		scene = new Scene(layout,800,600);
		shading = new ShadingFx(0.0, 0.0, 1.0, 1.0, Color.BLUEVIOLET, Color.ALICEBLUE);
		circlefx = new CircleFx(0.2, 0.24, 0.5, 0.5, shading, Color.GOLD, Color.GRAY, true);
		circle = (Ellipse) circlefx.createContent(scene);
		grad = new LinearGradient(shading.getX1(), shading.getY1(), shading.getX2(), shading.getY2(), true, 
				CycleMethod.NO_CYCLE, new Stop(0, shading.getColour1()), new Stop(1, shading.getColour2()));
		layout.getChildren().add(circle);
	}
	
	@Test
	public void testPositionAndDim() {
		assertEquals(circle.getCenterX(),360.0,0);
		assertEquals(circle.getCenterY(), 294.0,0);
		assertEquals(circle.getRadiusX()*2, 400.0,0);
		assertEquals(circle.getRadiusY()*2, 300.0,0);
	}
	
	@Test
	public void testContent() {
		assertNotNull(circle);
	}
	
	@Test
	public void testColours() {
		assertEquals(circle.getFill(), grad);
		assertEquals(circle.getStroke(), Color.GOLD);
	}

}
