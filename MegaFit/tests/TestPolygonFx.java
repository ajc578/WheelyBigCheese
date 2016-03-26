package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test PolygonFx
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import handlers.PolygonFx;
import handlers.ShadingFx;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;

public class TestPolygonFx {

	Group layout = new Group();
	PolygonFx polygonfx;
	ShadingFx shading;
	LinearGradient grad;
	Polygon polygon;
	Scene scene;
	double[] points = {240.0,294.0,320.0,342.0,288.0,408.0,192.0,408.0,160.0,342.0};
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() {
		scene = new Scene(layout,800,600);
		shading = new ShadingFx(0.0, 0.0, 1.0, 1.0, Color.BLUEVIOLET, Color.ALICEBLUE);
		polygonfx = new PolygonFx(0, 1000, "Pentagon.csv", shading, Color.GOLD, Color.GRAY, null);
		polygon = (Polygon) polygonfx.createContent(scene);
		grad = new LinearGradient(shading.getX1(), shading.getY1(), shading.getX2(), shading.getY2(), true, 
				CycleMethod.NO_CYCLE, new Stop(0, shading.getColour1()), new Stop(1, shading.getColour2()));
		layout.getChildren().add(polygon);
	}
	
	@Test
	public void testPointPositions() {
		for (int i = 0; i < points.length; i++) {
			assertEquals(polygonfx.getPoints()[i], points[i],0.1);
		}
	}
	
	@Test
	public void testShapeContentData() {
		assertEquals(polygonfx.getStartTime(), 0);
		assertEquals(polygonfx.getEndTime(), 1000);
		assertNull(polygonfx.getTargetLoc());
	}
	
	@Test
	public void testDetectGrad() {
		assertTrue(polygonfx.getDetectGrad());
	}
	
	@Test
	public void testContent() {
		assertNotNull(polygon);
	}
	
	@Test
	public void testColours() {
		assertEquals(polygon.getFill(), grad);
		assertEquals(polygon.getStroke(), Color.GOLD);
	}

}
