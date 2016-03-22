package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import handlers.CircleFx;
import handlers.RectangleFx;
import handlers.ShadingFx;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class TestRectangleFx {

	Group layout = new Group();
	RectangleFx rectanglefx;
	ShadingFx shading;
	LinearGradient grad;
	Rectangle rectangle;
	Scene scene;
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() {
		scene = new Scene(layout,800,600);
		shading = new ShadingFx(0.0, 0.0, 1.0, 1.0, Color.BLUEVIOLET, Color.ALICEBLUE);
		rectanglefx = new RectangleFx(0.2, 0.24, 0.5, 0.5, shading, Color.GOLD, Color.GRAY, true);
		rectangle = (Rectangle) rectanglefx.createContent(scene);
		grad = new LinearGradient(shading.getX1(), shading.getY1(), shading.getX2(), shading.getY2(), true, 
				CycleMethod.NO_CYCLE, new Stop(0, shading.getColour1()), new Stop(1, shading.getColour2()));
		layout.getChildren().add(rectangle);
	}
	
	@Test
	public void testPositionAndDim() {
		assertEquals(rectangle.getLayoutX(),160.0,0);
		assertEquals(rectangle.getLayoutY(), 144.0,0);
		assertEquals(rectangle.getWidth(), 400.0,0);
		assertEquals(rectangle.getHeight(), 300.0,0);
	}
	
	@Test
	public void testContent() {
		assertNotNull(rectangle);
	}
	
	@Test
	public void testColours() {
		assertEquals(rectangle.getFill(), grad);
		assertEquals(rectangle.getStroke(), Color.GOLD);
	}

}
