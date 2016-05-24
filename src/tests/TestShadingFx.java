package tests;

/*
 * Author : Oliver Rushton
 * Group: 4
 * Description: Test ShadingFx
 */


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.paint.Color;
import presentationViewer.ShadingFx;

public class TestShadingFx {

	ShadingFx shading;

	@Before
	public void setUp() {
		shading = new ShadingFx(0.0, 0.0, 1.0, 1.0, Color.BLUEVIOLET, Color.ALICEBLUE);
	}

	@Test
	public void testPositionAndDim() {
		assertEquals(shading.getX1(),0.0,0);
		assertEquals(shading.getY1(),0.0,0);
		assertEquals(shading.getX2(),1.0,0);
		assertEquals(shading.getY2(),1.0,0);
		assertEquals(shading.getColour1(),Color.BLUEVIOLET);
		assertEquals(shading.getColour2(),Color.ALICEBLUE);
	}

}