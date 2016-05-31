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

/**	The following test class tests if the ShadingFX class can
 *  successfully create an shaded object
 * **/
public class TestShadingFx {

	ShadingFx shading;
	/*Data Fields-
	 *shading - an object containing the details of the linear fill gradient if one is used.
	 * **/

	/**The following method sets up the testing framework. 
	 * It creates a shading object.
	 * **/
	@Before
	public void setUp() {
		shading = new ShadingFx(0.0, 0.0, 1.0, 1.0, Color.BLUEVIOLET, Color.ALICEBLUE);
	}

	/** 
	 * The following method asserts the position, dimension and colour
	 * properties of the test ShadingFx object
	 **/
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
